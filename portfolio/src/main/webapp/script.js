function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
  }
  
  function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
  }
  function myFunction() {
   var element = document.body;
   element.classList.toggle("dark-mode");
}


function printComment() {
  fetch('/data').then(response => response.json()).then((comments) => {
    const taskListElement = document.getElementById('comment-container');
    comments.forEach((comment) => {
      taskListElement.appendChild(createCommentElement(comment));
    })
  });
}

/** Creates an element that represents a comment */
function createCommentElement(comment) {
  const taskElement = document.createElement('li');
  taskElement.className = 'comment';
//   taskElement.id = "liStyle";

  const titleElement = document.createElement('span');
  titleElement.innerText = comment.name + ":" +comment.msg;
  taskElement.appendChild(titleElement);

  return taskElement;
}





