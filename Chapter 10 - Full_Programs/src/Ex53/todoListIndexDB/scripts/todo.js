window.onload = () => {
  // Hold an instance of a db object for us to store the IndexedDB data in
  let db;

  // All other UI elements we need for the app
  const taskList = document.getElementById('task-list');
  const taskForm = document.getElementById('task-form');
  const title = document.getElementById('title');
    
  // Let us open our database
  const DBOpenRequest = window.indexedDB.open('toDoList', 4);

  // Register two event handlers to act on the database being opened successfully, or not
  DBOpenRequest.onerror = (event) => {
    console.log('Error loading database.');
  };

  DBOpenRequest.onsuccess = (event) => {
    console.log('Database initialised.');

    // Store the result of opening the database in the db variable. This is used a lot below
    db = DBOpenRequest.result;

    // Run the displayData() function to populate the task list with all the to-do list data already in the IndexedDB
    displayData();
  };

  // This event handles the event whereby a new version of the database needs to be created
  // Either one has not been created before, or a new version number has been submitted via the
  // window.indexedDB.open line above
  //it is only implemented in recent browsers
  DBOpenRequest.onupgradeneeded = (event) => {
    db = event.target.result;

    db.onerror = (event) => {
      console.log('Error loading database.');
    };

    // Create an objectStore for this database
    const objectStore = db.createObjectStore('toDoList', { keyPath: 'taskTitle' });

    console.log('Object store created.');
  };

  function displayData() {
    // First clear the content of the task list so that you don't get a huge long list of duplicate stuff each time
    // the display is updated.
    while (taskList.firstChild) {
      taskList.removeChild(taskList.lastChild);
    }

    // Open our object store and then get a cursor list of all the different data items in the IDB to iterate through
    const objectStore = db.transaction('toDoList').objectStore('toDoList');
    objectStore.openCursor().onsuccess = (event) => {
      const cursor = event.target.result;
      // Check if there are no (more) cursor items to iterate through
      if (!cursor) {
        // No more items to iterate through, we quit.
        console.log('Entries all displayed.');
        return;
      }

      // Build the to-do list entry and put it into the list item.
      const taskTitle = cursor.value.taskTitle;
      const toDoText = `${taskTitle}`;
      const listItem = createListItem(toDoText);

      // Put the item item inside the task list
      taskList.appendChild(listItem);

      // Create a delete button inside each list item,
      const deleteButton = document.createElement('button');
      listItem.appendChild(deleteButton);
      deleteButton.textContent = 'X';
      
      // Set a data attribute on our delete button to associate the task it relates to.
      deleteButton.setAttribute('data-task', taskTitle);
      
      // Associate action (deletion) when clicked
      deleteButton.onclick = (event) => {
        deleteItem(event);
      };

      // continue on to the next item in the cursor
      cursor.continue();
    };
  };

  // Add listener for clicking the submit button
  taskForm.addEventListener('submit', addData, false);

  function addData(e) {
    // Prevent default, as we don't want the form to submit in the conventional way
    e.preventDefault();

    // Stop the form submitting if any values are left empty.
    // This should never happen as there is the required attribute
    if (title.value === '') {
      console.log('Data not submitted — form incomplete.');
      return;
    }
    
    // Grab the values entered into the form fields and store them in an object ready for being inserted into the IndexedDB
    const newItem = [
      { taskTitle: title.value },
    ];

    // Open a read/write DB transaction, ready for adding the data
    const transaction = db.transaction(['toDoList'], 'readwrite');

    // Report on the success of the transaction completing, when everything is done
    transaction.oncomplete = () => {
      console.log('Transaction completed: database modification finished.');

      // Update the display of data to show the newly added item, by running displayData() again.
      displayData();
    };

    // Handler for any unexpected error
    transaction.onerror = () => {
      console.log(`Transaction not opened due to error: ${transaction.error}`);
    };

    // Call an object store that's already been added to the database
    const objectStore = transaction.objectStore('toDoList');
    console.log(objectStore.indexNames);
    console.log(objectStore.keyPath);
    console.log(objectStore.name);
    console.log(objectStore.transaction);
    console.log(objectStore.autoIncrement);

    // Make a request to add our newItem object to the object store
    const objectStoreRequest = objectStore.add(newItem[0]);
    objectStoreRequest.onsuccess = (event) => {

      // Report the success of our request
      // (to detect whether it has been succesfully
      // added to the database, you'd look at transaction.oncomplete)
      console.log('Request successful.');

      // Clear the form, ready for adding the next entry
      title.value = '';
    };
  };

  function deleteItem(event) {
    // Retrieve the name of the task we want to delete
    const dataTask = event.target.getAttribute('data-task');

    // Open a database transaction and delete the task, finding it by the name we retrieved above
    const transaction = db.transaction(['toDoList'], 'readwrite');
    transaction.objectStore('toDoList').delete(dataTask);

    // Report that the data item has been deleted
    transaction.oncomplete = () => {
      // Delete the parent of the button, which is the list item, so it is no longer displayed
      event.target.parentNode.parentNode.removeChild(event.target.parentNode);
      console.log(`Task "${dataTask}" deleted.`);
    };
  };

  function createListItem(contents) {
      const listItem = document.createElement('li');
      listItem.textContent = contents;
      return listItem;
    };

}

