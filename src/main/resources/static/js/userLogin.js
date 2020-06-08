const attemptLogin = function attemptLogin() {
  const usernameInput = $("#loginUsername").val().trim();
  const passwordInput = $("#loginPassword").val().trim();

  $("#login").prop("disabled", true);
  login(usernameInput, passwordInput);
}

const login= function login(usernameInput, passwordInput) {
  $.ajax({
    url: "/login",
    type: "POST",
    data: JSON.stringify({"username": usernameInput, "password": passwordInput}),
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-Token": getToken()
    },
    success: [function () {
      $("#loginMsg")
        .text("Login successful")
        .fadeIn('slow')
        .delay(5000)
        .fadeOut('slow');
      window.location = "/";
      localStorage.setItem("logged", "true");
    }],
    error: [function (data) {
      $("#loginMsg")
        .text("Login failed: " + JSON.parse(JSON.stringify(data)).responseJSON.message)
        .fadeIn('slow')
        .delay(5000)
        .fadeOut('slow');
    }]
  })
};

const attemptRegister = function attemptRegister() {
  const email = $("#registerEmail").val().trim();
  const username = $("#registerUsername").val().trim();
  const password = $("#registerPassword").val().trim();
  const confirmPassword = $("#registerConfirmPassword").val().trim();

  $("#register").prop("disabled", true);
  register(email, username, password, confirmPassword);
}

const register= function register(email, username, password, confirmPassword) {
  $.ajax({
    url: "/register",
    type: "POST",
    data : JSON.stringify({"email": email, "username": username, "password": password, "confirmPassword": confirmPassword}),
    headers : {
      "Content-Type" : "application/json",
      "X-XSRF-Token": getToken()
    },
    success: [function () {
      window.location = "/";
      window.alert("Registered Successfully!");
    }],
    error: [function (data) {
      $("#registerMsg")
        .text("Login failed: " + JSON.parse(JSON.stringify(data)).responseJSON.message)
        .fadeIn('slow')
        .delay(5000)
        .fadeOut('slow');
    }]
  })
};

function logout() {
  $.ajax({
    url: "/logout",
    type: "POST",
    headers: {
      "Content-Type": "application/json",
      "X-XSRF-Token": getToken()
    },
    success: [function () {
      localStorage.setItem("logged", "false");
      window.location = "/";
    }]
  })
}

function getToken() {
  var nameEQ = "XSRF-TOKEN" + "=";
  var ca = document.cookie.split(';');
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') c = c.substring(1, c.length);
    if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
  }
  return null;
}

function hideLoginShowRegister() {
  var login = document.getElementById("login-form");
  var register = document.getElementById("register-form");
  login.style.display = "none";
  register.style.display = "block";
}

function hideRegisterShowLogin() {
  var login = document.getElementById("login-form");
  var register = document.getElementById("register-form");
  register.style.display = "none";
  login.style.display = "block";
}

function checkIfLogged() {
  let logged = localStorage.getItem("logged")
  if (logged != null && logged.localeCompare("true") === 0) {
    $("#logOut").show();
    $("#uploadButton").show();
  } else {
    $("#logOut").hide();
    $("#uploadButton").hide();
  }
}