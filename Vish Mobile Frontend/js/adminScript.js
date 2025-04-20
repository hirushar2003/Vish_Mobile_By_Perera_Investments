$(document).ready(function() {
  const isLoggedIn = localStorage.getItem('isLoggedIn');
  if (isLoggedIn === 'true') {
    showMainApp();
  }

  $('.auth-tab').click(function() {
    const tabId = $(this).data('tab');

    $('.auth-tab').removeClass('active');
    $(this).addClass('active');

    // Show tab content
    $('.auth-page').removeClass('active');
    $('#' + tabId).addClass('active');
  });

  // Footer links
  $('#to-login').click(function(e) {
    e.preventDefault();
    $('.auth-tab[data-tab="login"]').click();
  });

  $('#to-signup').click(function(e) {
    e.preventDefault();
    $('.auth-tab[data-tab="signup"]').click();
  });

  // Toggle password visibility
  $('.toggle-password').click(function() {
    const targetId = $(this).data('target');
    const input = $('#' + targetId);
    const icon = $(this).find('i');

    if (input.attr('type') === 'password') {
      input.attr('type', 'text');
      icon.removeClass('fa-eye').addClass('fa-eye-slash');
    } else {
      input.attr('type', 'password');
      icon.removeClass('fa-eye-slash').addClass('fa-eye');
    }
  });

  // Password validation
  $('#password').focus(function() {
    $('.password-validation').slideDown(200);
  });

  $('#password').blur(function() {
    if (!$(this).val()) {
      $('.password-validation').slideUp(200);
    }
  });

  $('#password').keyup(function() {
    const password = $(this).val();

    // Length check
    if (password.length >= 8) {
      $('.length-check').addClass('valid').removeClass('invalid');
      $('.length-check i').removeClass('fa-times-circle').addClass('fa-check-circle');
    } else {
      $('.length-check').removeClass('valid').addClass('invalid');
      $('.length-check i').removeClass('fa-check-circle').addClass('fa-times-circle');
    }

    // Uppercase letter check
    if (/[A-Z]/.test(password)) {
      $('.uppercase-check').addClass('valid').removeClass('invalid');
      $('.uppercase-check i').removeClass('fa-times-circle').addClass('fa-check-circle');
    } else {
      $('.uppercase-check').removeClass('valid').addClass('invalid');
      $('.uppercase-check i').removeClass('fa-check-circle').addClass('fa-times-circle');
    }

    // Number check
    if (/\d/.test(password)) {
      $('.number-check').addClass('valid').removeClass('invalid');
      $('.number-check i').removeClass('fa-times-circle').addClass('fa-check-circle');
    } else {
      $('.number-check').removeClass('valid').addClass('invalid');
      $('.number-check i').removeClass('fa-check-circle').addClass('fa-times-circle');
    }

    // Special character check
    if (/[^A-Za-z0-9]/.test(password)) {
      $('.special-check').addClass('valid').removeClass('invalid');
      $('.special-check i').removeClass('fa-times-circle').addClass('fa-check-circle');
    } else {
      $('.special-check').removeClass('valid').addClass('invalid');
      $('.special-check i').removeClass('fa-check-circle').addClass('fa-times-circle');
    }
  });

  //------------------------------------------------- Signup form submission
  $(document).ready(function () {
    $('#signup-form').submit(function (e) {
      e.preventDefault();

      let isValid = true;
      const name = $('#name').val(); // User's name
      const email = $('#email').val();
      const password = $('#password').val();
      const contactNumber = $('#contact_number').val();
      const address = $('#address').val();
      const terms = $('#terms').prop('checked');

      // Basic validation
      if (!name) {
        $('#name-error').show();
        isValid = false;
      } else {
        $('#name-error').hide();
      }

      if (!email || !validateEmail(email)) {
        $('#email-error').show();
        isValid = false;
      } else {
        $('#email-error').hide();
      }

      if (!password || !validatePassword(password)) {
        $('#password-error').show();
        isValid = false;
      } else {
        $('#password-error').hide();
      }

      if (!contactNumber) {
        $('#contact-error').show();
        isValid = false;
      } else {
        $('#contact-error').hide();
      }

      if (!address) {
        $('#address-error').show();
        isValid = false;
      } else {
        $('#address-error').hide();
      }

      if (!terms) {
        isValid = false;
      }

      if (!isValid) return;

      // Create user object with 'username' set as 'name'
      const userData = {
        username: name,  // Assign 'name' to 'username'
        email: email,
        password: password,
        contactNumber: contactNumber,
        address: address,
        userType: 'admin' // Set userType to customer
      };

      console.log(userData)

      // Send registration request
      $.ajax({
        url: "http://localhost:8080/api/v1/user/auth/register",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(userData),
        success: function (response) {
          localStorage.setItem("token", response.token);
          localStorage.setItem("email", response.email);
          localStorage.setItem("username", response.username);
          localStorage.setItem("userId", response.id);
          localStorage.setItem("contactNumber", response.contactNumber);
          localStorage.setItem("address", response.address);
          localStorage.setItem("isLoggedIn", "true");

          showMainApp();
          showNotification("Account created successfully!");

          // Fetch user data and navigate to the main page

        },
        error: function (xhr) {
          showNotification("Registration failed: " + xhr.responseJSON.message, "error");
        }
      });
    });
  });


  // ------------------------------------------------Login form submission
  $('#login-form').submit(function(e) {
    e.preventDefault();

    let isValid = true;
    const email = $('#login-email').val();
    const password = $('#login-password').val();

    // Basic validation
    if (!email || !validateEmail(email)) {
      $('#login-email-error').show();
      isValid = false;
    } else {
      $('#login-email-error').hide();
    }

    if (!password) {
      $('#login-password-error').show();
      isValid = false;
    } else {
      $('#login-password-error').hide();
    }

    if (isValid) {
      // In a real app, you would verify credentials with your backend
      // For demo purposes, just store that user is logged in
      localStorage.setItem('isLoggedIn', 'true');
      localStorage.setItem('user', JSON.stringify({
        email: email
      }));

      // Show success notification
      showNotification('Login successful!');

      // Show main app after a brief delay
      setTimeout(function() {
        showMainApp();
      }, 1500);
    }
  });

  // Logout button
  $('#logout-btn').click(function(e) {
    e.preventDefault();
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('user');

    // Show auth pages
    $('#spa-container').hide();
    $('#auth-wrapper').show();

    // Show notification
    showNotification('Logged out successfully!');
  });

  // Helper functions
  function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  }

  function validatePassword(password) {
    // At least 8 characters, 1 uppercase, 1 number, 1 special char
    const re = /^(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/;
    return re.test(password);
  }

  // ----------------------------------Customize notification----------
  function showNotification(message) {
    $('#notification').text(message);
    $('#notification').fadeIn();

    setTimeout(function() {
      $('#notification').fadeOut();
    }, 3000);
  }

  function showMainApp() {
    $('#auth-wrapper').hide();
    $('#spa-container').show();

    // Initialize your existing SPA functionality
    // Navigation
    $('.nav-link').click(function(e) {
      e.preventDefault();
      const sectionId = $(this).data('section');

      if (sectionId) { // Skip for logout button
        // Update navigation
        $('.nav-link').removeClass('active');
        $(this).addClass('active');

        // Show section
        $('section').removeClass('active');
        $('#' + sectionId).addClass('active');
      }
    });
  }

  // Initialize your existing SPA code for logged-in users
  if (isLoggedIn === 'true') {
    // Tab navigation
    $('.tab').click(function() {
      const tabId = $(this).data('tab');

      // Update tabs
      $('.tab').removeClass('active');
      $(this).addClass('active');

      // Show tab content
      $('.tab-content').removeClass('active');
      $('#' + tabId).addClass('active');
    });

    // Other existing functionality...
  }
});

$('#battery-health').on('input', function() {
  $('#battery-health-value').text(this.value + '%');
});

let selectedFiles = []; // Declare selectedFiles globally


$(document).ready(function () {
  // let selectedFiles = [];

  $("#trigger-upload").click(function () {
    $("#phone-image").click();
  });

  $("#phone-image").on("change", function () {
    let files = Array.from(this.files);

    if (files.length + selectedFiles.length > 5) {
      alert(`You can only upload ${5 - selectedFiles.length} more images.`);
      return;
    }

    selectedFiles.push(...files);

    files.forEach((file) => {
      let reader = new FileReader();
      reader.onload = function (e) {
        let imageHtml = `
          <div class="image-preview-item">
            <img src="${e.target.result}" alt="Phone Image" />
            <div class="image-preview-overlay">
              <button class="delete-image" onclick="deleteImage('${file.name}', this)">
                <i class="fas fa-trash"></i>
              </button>
            </div>
          </div>`;
        $("#upload-placeholder").before(imageHtml);
      };
      reader.readAsDataURL(file);
    });
  });

  window.deleteImage = function (fileName, element) {
    $(element).closest(".image-preview-item").remove();
    selectedFiles = selectedFiles.filter(file => file.name !== fileName);
  };

  $("#add-phone-btn").click(async function (event) {
    event.preventDefault();

    let token = localStorage.getItem("token");
    if (!token) {
      alert("Please login to add a phone.");
      return;
    }

    let phoneData = {
      model: $("select[name='phone-model']").val(),
      imei: $("input[placeholder='Enter IMEI number']").val(),
      capacity: $("select[name='capacity']").val(),
      color: $("select[name='color']").val(),
      batteryHealth: $("#battery-health").val(),
      boughtPrice: $("input[placeholder='Enter bought price']").val(),
      sellingPrice: $("input[placeholder='Enter selling price']").val(),
    };

    let uploadedImages = await uploadImagesToCloudinary();
    if (uploadedImages.length === 0) {
      alert("Please upload at least one image.");
      return;
    }

    $.ajax({
      url: "http://localhost:8080/api/v1/sellingPhone/save",
      type: "POST",
      contentType: "application/json",
      headers: {
        "Authorization": "Bearer " + token
      },
      data: JSON.stringify(phoneData),
      success: function () {
        console.log("Phone saved successfully! Now fetching last inserted ID...");

        // Step 2: Fetch the last inserted ID
        $.ajax({
          url: "http://localhost:8080/api/v1/sellingPhone/lastId",
          type: "GET",
          headers: {
            "Authorization": "Bearer " + token
          },
          success: function (response) {
            if (response && response.id) {
              console.log("Last Inserted Phone ID:", response.id);
              localStorage.setItem("phoneId", response.id);
              savePhonePhotos(response.id, uploadedImages);
            } else {
              console.log("Failed to retrieve last ID.");
            }
          },
          error: function (error) {
            console.error("Error fetching last inserted ID:", error);
          }
        });
      },
      error: function (error) {
        console.error("Error saving phone:", error);
        alert("Failed to save phone. Try again.");
      }
    });


  });

  async function uploadImagesToCloudinary() {
    if (selectedFiles.length === 0) return [];

    let cloudinaryUploadUrl = "https://api.cloudinary.com/v1_1/duxanz6pf/image/upload";
    let cloudinaryUploadPreset = "phone_photos";
    let imageUrls = [];

    for (let file of selectedFiles) {
      let formData = new FormData();
      formData.append("file", file);
      formData.append("upload_preset", cloudinaryUploadPreset);

      try {
        let response = await $.ajax({
          url: cloudinaryUploadUrl,
          type: "POST",
          data: formData,
          processData: false,
          contentType: false,
          dataType: "json"
        });

        if (response.secure_url) {
          imageUrls.push(response.secure_url);
        } else {
          console.error("Unexpected Cloudinary response:", response);
          alert("Image upload failed.");
        }
      } catch (error) {
        console.error("Image upload error:", error);
        alert("Image upload failed. Try again.");
      }
    }
    return imageUrls;
  }

  function savePhonePhotos(phoneId, imageUrls) {
    let photoData = imageUrls.map(url => ({ phoneId, photoUrl: url }));
    console.log(photoData);

    let token = localStorage.getItem("token");
    if (!token) {
      alert("Please login to add photos.");
      return;
    }

    $.ajax({
      url: "http://localhost:8080/api/v1/sellingPhonePhoto/save",
      type: "POST",
      contentType: "application/json",
      headers: { "Authorization": "Bearer " + token },
      data: JSON.stringify(photoData),
      success: function (response) {
        alert("Phone and photos added successfully!");
        location.reload();
      },
      error: function (error) {
        console.error("Error saving photos:", error);
        alert("Failed to save photos.");
      }
    });
  }
});



$(document).ready(function () {

// Function to remove image from preview & global array
  window.deleteImage = function (fileName, element) {
    $(element).closest(".image-preview-item").remove();
    selectedFiles = selectedFiles.filter(file => file.name !== fileName);
    updateUploadUI();
    console.log("Updated selectedFiles after delete:", selectedFiles); // Debugging
  };



  function updateUploadUI() {
    if (imageCount >= maxImages) {
      uploadPlaceholder.hide();
    } else {
      uploadPlaceholder.show();
    }
  }
});










































$(document).ready(function () {
  // Fetch phone data after registration (or whenever you need)
  let token = localStorage.getItem("token");

  // Make sure the token exists
  if (token) {
    console.log("Token found:", token); // Debugging line

    $.ajax({
      url: "http://localhost:8080/api/v1/sellingPhone/getAll",
      type: "GET",
      beforeSend: showLoader,
      contentType: "application/json",
      headers: {
        "Authorization": "Bearer " + token  // Include the token in the Authorization header
      },
      success: function (response) {
        console.log("Phone data:", response);

        if (!Array.isArray(response.data)) {
          console.error("Expected an array but got:", response);
          return;
        }

        let phones = response.data; // Extract the actual phone list
        let phoneTableBody = $(".admin-phone-view tbody");
        phoneTableBody.empty();

        phones.forEach(function (phone) {
          let batteryColor = getBatteryColor(phone.batteryHealth);
          let phoneRow = `
            <tr>
                <td>${phone.id}</td>
                <td>${phone.model}</td>
                <td>${phone.imei}</td>
                <td>${phone.capacity}</td>
                <td>
                    <span class="color-preview" style="background-color: ${phone.colorCode};"></span> ${phone.color}
                </td>
                <td>
                    <div class="battery-health">
                        <div class="battery-fill" style="width: ${phone.batteryHealth}%; background-color: ${batteryColor};">
                            ${phone.batteryHealth}%
                        </div>
                    </div>
                </td>
                <td>$${phone.boughtPrice.toFixed(2)}</td>
                <td>$${phone.sellingPrice.toFixed(2)}</td>
                <td>
                    <div class="action-btns">
                        <button class="btn btn-small btn-outline edit-btn" data-id="${phone.id}">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-small btn-danger delete-btn" data-id="${phone.id}">
                            <i class="fas fa-trash"></i>
                        </button>
                        <button class="btn btn-small image-btn" data-id="${phone.id}">
                            <i class="fas fa-images"></i>
                        </button>
                    </div>
                </td>
            </tr>
          `;
          phoneTableBody.append(phoneRow);
        });
      },
      error: function (error) {
        console.error("Error fetching phone details:", error);
        alert("Failed to retrieve phone details. Try again.");
      },
      complete: hideLoader
    });
  } else {
    console.error("No token found, please login.");
  }
});


// Function to determine battery color based on health percentage
function getBatteryColor(percentage) {
  if (percentage >= 90) return "#4CAF50";
  else if (percentage >= 70) return "#FFC107";
  else return "#F44336";
}


$(document).on("click", ".delete-btn", function () {
  let phoneId = $(this).data("id"); // Get phone ID
  console.log("Phone ID to delete:", phoneId); // Debugging

  if (confirm(`Are you sure you want to delete phone ID ${phoneId}?`)) {
    $.ajax({
      url: `http://localhost:8080/api/v1/sellingPhone/delete/${phoneId}`,
      type: "DELETE",
      success: function (response) {
        alert(`Phone ID ${phoneId} deleted successfully!`);
        $(`button[data-id="${phoneId}"]`).closest("tr").remove();
      },
      error: function (xhr, status, error) {
        console.error("Error deleting phone:", error);
        alert("Failed to delete phone. Please try again.");
      }
    });
  }
});



$(document).on("click", ".edit-btn", function () {
  let row = $(this).closest("tr");

  let model = row.find("td:eq(1)").text().trim();
  let imei = row.find("td:eq(2)").text().trim();
  let capacity = row.find("td:eq(3)").text().trim();
  let color = row.find("td:eq(4)").text().trim();
  let batteryHealth = row.find("td:eq(5)").text().trim().replace('%', ''); // Removing '%' sign
  let boughtPrice = row.find("td:eq(6)").text().trim().replace('LKR', '').trim(); // Removing 'LKR' text
  let sellingPrice = row.find("td:eq(7)").text().trim().replace('LKR', '').trim(); // Removing 'LKR' text

  $("#phone-model").val(model);
  $("#imei-number").val(imei);
  $("#capacity").val(capacity);
  $("#color").val(color);
  $("#battery-health").val(batteryHealth).next('span').text(batteryHealth + "%"); // Update range value display
  $("#bought-price").val(boughtPrice);
  $("#selling-price").val(sellingPrice);

  const addButton = document.getElementById('add-phone-btn');
  addButton.id = 'update-phone-btn';
  addButton.innerHTML = '<i class="fas fa-edit"></i> Update Phone';

  let phoneId = row.find("td:eq(0)").text().trim();
  fetchPhoneImages(phoneId);
});

function fetchPhoneImages(phoneId) {
  let token = localStorage.getItem("token");

  if (token) {
    $.ajax({
      url: `http://localhost:8080/api/v1/sellingPhonePhoto/getPhotoByPhoneId/${phoneId}`, // Fetch images for the phone by phoneId
      type: "GET",
      contentType: "application/json",
      headers: {
        "Authorization": "Bearer " + token
      },
      success: function (response) {
        if (response && response.data) {
          let photoUrls = response.data;

          // Clear existing images and add new ones
          let imagePreviewContainer = $("#image-preview-container");
          imagePreviewContainer.empty();

          // Populate the photo preview container with the fetched image URLs
          photoUrls.forEach(url => {
            imagePreviewContainer.append(`
              <div class="image-preview-item">
                <img src="${url}" alt="Phone Image" class="phone-image" />
                <div class="image-preview-overlay">
                  <button class="delete-image" onclick="deleteImageFromServer('${url}', this)">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
              </div>
            `);
          });

          // Always append the input field to allow users to upload more images
          let uploadedImagesCount = photoUrls.length;
          if (uploadedImagesCount < 5) {
            imagePreviewContainer.append(`
              <div class="photo-preview-container">
                <div class="upload-placeholder" id="upload-placeholder" onclick="document.getElementById('phone-image').click();">
                    <i class="fas fa-plus" style="font-size: 24px;"></i>
                </div>
              </div>
            `);
          }

          // Update the uploaded count
          $(".upload-count span:nth-child(2)").text(`${photoUrls.length}/5 uploaded`);

          // Show/Hide placeholder based on the number of uploaded images
          updateUploadUI(uploadedImagesCount);
        }
      },
      error: function (error) {
        console.error("Error fetching images:", error);
      }
    });
  } else {
    alert("No token found, please login.");
  }
}



// New delete function for removing images from preview and server
function deleteImageFromServer(imageUrl, element) {
  let token = localStorage.getItem("token");

  if (!token) {
    alert("Please login to delete images.");
    return;
  }

  $.ajax({
    url: `http://localhost:8080/api/v1/sellingPhonePhoto/delete`, // Assuming a DELETE API exists for removing the image
    type: "DELETE",
    contentType: "application/json",
    headers: {
      "Authorization": "Bearer " + token
    },
    data: JSON.stringify({ imageUrl }), // Send the image URL to delete
    success: function () {
      // Remove the image from the preview container
      $(element).closest(".image-preview-item").remove();
      updateUploadUI();
    },
    error: function (error) {
      console.error("Error deleting image:", error);
      alert("Failed to delete image.");
    }
  });
}

// Update the UI to allow adding images if there are less than 5
function updateUploadUI(uploadedImagesCount = 0) {
  let maxImages = 5;

  if (uploadedImagesCount >= maxImages) {
    $("#upload-placeholder").hide(); // Hide the upload button if the max is reached
  } else {
    $("#upload-placeholder").show(); // Show the upload button if there's space
  }
}






$(document).on("click", ".image-btn", function () {
  let row = $(this).closest("tr");
  let phoneId = row.find("td:eq(0)").text().trim();
  let token = localStorage.getItem("token");

  if (token) {
    $.ajax({
      url: `http://localhost:8080/api/v1/sellingPhonePhoto/getPhotoByPhoneId/${phoneId}`,
      type: "GET",
      contentType: "application/json",
      headers: {
        "Authorization": "Bearer " + token
      },
      success: function (response) {
        if (response && response.data) {
          let photoUrls = response.data;
          console.log(`Photos for phone ID ${phoneId}:`, photoUrls);
        }
      },
      error: function (error) {
        console.error("Error fetching photos:", error);
        alert("Failed to load photos.");
      }
    });
  } else {
    alert("No token found, please login.");
  }
});










// --------------------BUYING PRICE MANAGEMENT PAGE JS ---------------------------------------------------------


$(document).ready(function() {
  const token = localStorage.getItem('token');
  $.ajax({
    url: "http://localhost:8080/api/v1/phoneBuyingPrice/getAllPhonePrices",
    type: "GET",
    contentType: "application/json",
    headers: {
      "Authorization": `Bearer ${token}`,
    },
    success: function(response) {
      if (response.statusCode === 200) {
        const phonePrices = response.data;
        $("#prices-list tbody").empty();
        phonePrices.forEach(price => {
          const row = `
                <tr>
                    <td>${price.id}</td>
                    <td>${price.model}</td>
                    <td>${price.storage}</td>
                    <td>${price.color}</td>
                    <td><span class="color-preview" style="background-color: ${price.colorCode};"></span></td>
                    <td>$${price.price}</td>
                    <td>
                        <div class="action-btns">
                            <!--<button class="btn btn-small btn-outline" onclick="editPrice(${price.id})"><i class="fas fa-edit"></i></button>-->
                            <button class="btn btn-small btn-danger" data-id="${price.id}"><i class="fas fa-trash"></i></button>
                        </div>
                    </td>
                </tr>
            `;
          $("#prices-list tbody").append(row);
        });

      } else {
        alert("Failed to fetch phone buying prices");
      }
    },
    error: function(xhr, status, error) {
      console.error("Error fetching phone buying prices:", error);
      alert("An error occurred while fetching data.");
    }
  });
});

$(".save-price").on("click", function (event) {
  event.preventDefault();
  let token = localStorage.getItem('token');
  console.log(token);

  const phoneBuyingPrice = {
    model: $("select[name='price-phone-model']").val(),
    storage: $("select[name='price-capacity']").val(),
    color: $("select[name='price-color']").val(),
    colorCode: $("input[name='price-color-code']").val(),
    price: $("input[name='price-price']").val()
  };
  $.ajax({
    url: "http://localhost:8080/api/v1/phoneBuyingPrice/savePhonePrice",
    type: "POST",
    contentType: "application/json",
    headers: {
      "Authorization": "Bearer " + token
    },
    data: JSON.stringify(phoneBuyingPrice),
    success: function () {
      alert("phone buying price saved successfully.");
      $("#price-form")[0].reset();
    },
    error: function (error) {
      alert("Failed to save phone buying price, Try again." + error);
    }
  });
});

$(document).on("click", ".btn-danger", function () {
  let token = localStorage.getItem("token");
  let buyingPhonePriceId = $(this).data("id");
  console.log("Phone ID to delete:", buyingPhonePriceId);

  if (confirm(`Are you sure you want to delete phone ID ${buyingPhonePriceId}?`)) {
    $.ajax({
      url: `http://localhost:8080/api/v1/phoneBuyingPrice/deletePhoneBuyingPrice/${buyingPhonePriceId}`,
      type: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`,
      },
      success: function (response) {
        alert(`Phone ID ${buyingPhonePriceId} deleted successfully!`);
        $(`button[data-id="${buyingPhonePriceId}"]`).closest("tr").remove();
      },
      error: function (xhr, status, error) {
        console.error("Error deleting buying phone price details :", error);
        alert("Failed to delete buying phone price details. Please try again.");
      }
    });
  }
});



// ----------------------------------------USER MANAGEMENT ------------------------------------------------

document.addEventListener("DOMContentLoaded", function () {
  fetchCustomers();
});

function fetchCustomers() {
  const token = localStorage.getItem("token");

  if (!token) {
    console.error("No authentication token found. Please log in.");
    return;
  }

  fetch("http://localhost:8080/api/v1/userManage/getAllCustomers", {
    method: "GET",
    contentType: "application/json",
    headers: {
      "Authorization": `Bearer ${token}`, // Attach JWT token
    }
  })
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      populateCustomerTable(data);
    })
    .catch(error => {
      console.error("Error fetching customers:", error);
    });
}

function populateCustomerTable(customers) {
  const tableBody = document.querySelector("#user-management tbody");
  tableBody.innerHTML = ""; // Clear previous data

  customers.forEach(customer => {
    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${customer.id}</td>
        <td>${customer.username}</td>
        <td>${customer.email}</td>
        <td>${customer.contactNumber || "N/A"}</td>
        <td>${customer.address || "N/A"}</td>
        <td>
          <span class="status-badge ${customer.status === "ACTIVE" ? "status-active" : "status-inactive"}">
            ${customer.status}
          </span>
        </td>
        <td>
          <div class="action-btns">
            <button class="btn btn-small btn-outline"><i class="fas fa-eye"></i></button>
            ${customer.status === "ACTIVE" ? `
              <button class="btn btn-small btn-danger-customer customer-deactive" onclick="changeUserStatus(${customer.id}, 'DEACTIVE')">
                <i class="fas fa-ban"></i> Deactivate
              </button>`
      :
      `<button class="btn btn-small btn-success-customer customer-active" onclick="changeUserStatus(${customer.id}, 'ACTIVE')">
                <i class="fas fa-check"></i> Activate
              </button>`}
          </div>
        </td>
      `;

    tableBody.appendChild(row);
  });
}

function changeUserStatus(userId, newStatus) {
  const token = localStorage.getItem("token");

  fetch(`http://localhost:8080/api/v1/userManage/changeStatus/${userId}`, {
    method: "PUT",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ status: newStatus })
  })
    .then(response => response.json())
    .then(updatedUser => {
      console.log(updatedUser);
      alert(`User status changed to ${updatedUser.status}`);
      fetchCustomers();
    })
    .catch(error => console.error("Error updating user status:", error));
}



// ----------------------------------Phone Approval Page---------------------------

function getAllTradePhonesWithPhotos() {
  const token = localStorage.getItem("token"); // or however you're storing it
  const API_URL = "http://localhost:8080/api/v1/adminTradePhone/getAllTradePhonesWithPhotos";

  $.ajax({
    url: API_URL,
    type: "GET",
    headers: {
      "Authorization": "Bearer " + token
    },
    success: function (data) {
      const container = $("#phone-approval-container");
      container.empty();

      data.forEach(phone => {
        const {
          id,
          model,
          storage,
          batteryHealth,
          frameCondition,
          box,
          colour,
          willingTo,
          userId,
          photoUrls
        } = phone;

        const photoUrl = photoUrls && photoUrls.length > 0 ? photoUrls[0] : "/img/default.jpg";

        const card = `
          <div class="phone-approval-card">
            <div class="phone-approval-card-header">
              <h4>${model || "N/A"}</h4>
            </div>
            <div class="phone-approval-card-body">
              <div class="phone-approval-card-image">
                <img src="${photoUrl}" alt="phone-image">
              </div>
              <div class="phone-approval-card-content">
                <div class="phone-approval-card-content-specs">
                  ${generateSpec("Storage", storage)}
                  ${generateSpec("Battery health", batteryHealth)}
                  ${generateSpec("Frame condition", frameCondition)}
                  ${generateSpec("Box available", box === "AVAILABLE" ? "Yes" : "No")}
                  ${generateSpec("Color", colour)}
                  ${generateSpec("Willing to", formatWillingTo(willingTo))}
                  ${generateSpec("User", userId)}
                </div>
                <div class="phone-approval-card-content-buttons">
                  <button class="phone-approval-card-btn-view" onclick="viewMore(${id})">View more</button>
                  <button class="phone-approval-card-btn-approve" onclick="approvePhone(${id})">Approve</button>
                  <button class="phone-approval-card-btn-decline" onclick="declinePhone(${id})">Decline</button>
                  <button class="phone-approval-card-btn-email" onclick="sendEmailToUser(${userId})">Send email</button>
                </div>
              </div>
            </div>
          </div>
        `;

        container.append(card);
      });
    },
    error: function (err) {
      console.error("Error fetching trade phone data:", err);
    }
  });

  // Helper functions
  function generateSpec(label, value) {
    return `
      <div class="phone-approval-card-content-specs-group">
        <div class="phone-approval-card-content-specs-group-header">
          <p>${label} :</p>
        </div>
        <div class="phone-approval-card-content-specs-group-body">
          ${value ?? "N/A"}
        </div>
      </div>
    `;
  }

  function formatWillingTo(value) {
    if (!value) return "N/A";
    return value.toUpperCase() === "SELL" ? "Sell" : "Sell/Exchange";
  }

  // Placeholder action functions
  window.viewMore = function (id) {
    console.log("View more for phone ID:", id);
  };

  window.approvePhone = function (id) {
    console.log("Approve phone ID:", id);
  };

  window.declinePhone = function (id) {
    console.log("Decline phone ID:", id);
  };

  window.sendEmailToUser = function (userId) {
    console.log("Send email to user ID:", userId);
  };
}

// Call the function on page load
$(document).ready(function () {
  getAllTradePhonesWithPhotos();
});




function showLoader() {
  const overlay = document.getElementById("loading-overlay");
  overlay.style.display = "flex";
}

function hideLoader() {
  const overlay = document.getElementById("loading-overlay");
  overlay.style.display = "none";
}
