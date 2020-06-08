$(document).ready(function() {
  $("#input-b9").fileinput({
    showPreview: false,
    showUpload: false,
    elErrorContainer: '#kartik-file-errors',
    allowedFileExtensions: ["db", "sqlite", "sqlitedb"]
  });
});

function readFile() {
  const selectedFile = document.getElementById("input-b9").files[0];
  var data = new FormData();
  data.append("file", selectedFile);
  upload(data);
}

function upload(data) {
  $.ajax({
    url: "/upload",
    type: "POST",
    data : data,
    enctype : "multipart/form-data",
    contentType : false,
    cache : false,
    processData : false,
    headers : {
      "X-XSRF-Token": getToken()
    },
    success: [function () {

      window.alert("Upload successful!");
    }],
    error: [function (data) {
      window.alert("Upload Failed " + JSON.parse(JSON.stringify(data)).responseJSON.message);
    }]
  })
}