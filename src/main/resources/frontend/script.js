// Authorization header value
let groupId = prompt("enter group id");
let apiUrl = 'http://localhost:8080/api/tasks/' + groupId;

let token = prompt("enter bearer token")
const authorizationHeader = 'Bearer ' + token;

// Function to fetch tasks from the API
function fetchTasks() {
     const headers = new Headers();
     headers.append('Authorization', authorizationHeader);
     fetch(apiUrl, {
       headers: headers
     })
    .then(response => response.json())
    .then(tasks => {

      tasks.sort((a, b) => a.order - b.order);

      const taskContainer = document.getElementById('taskContainer');
      taskContainer.innerHTML = ''; // Clear the container

      tasks.forEach(task => {
        const taskElement = document.createElement('div');
        taskElement.classList.add('task');

        const titleElement = document.createElement('div');
        titleElement.classList.add('title');
        titleElement.textContent = task.title;
        taskElement.appendChild(titleElement);

        const descriptionElement = document.createElement('div');
        descriptionElement.classList.add('description');
        descriptionElement.textContent = task.description;
        taskElement.appendChild(descriptionElement);

        if (task.deadline !== null) {
          const deadlineElement = document.createElement('div');
          deadlineElement.classList.add('deadline');
          deadlineElement.textContent = `Дедлайн: ${new Date(task.deadline).toLocaleString()}`;
          taskElement.appendChild(deadlineElement);
        }

        const statusElement = document.createElement('div');
        statusElement.classList.add('status');
        statusElement.textContent = `Статус: ${task.status.caption}`;
        taskElement.appendChild(statusElement);

        taskContainer.appendChild(taskElement);
      });
    })
    .catch(error => console.error('Error fetching tasks:', error));
}

// Call the fetchTasks function when the page loads
window.addEventListener('load', fetchTasks);