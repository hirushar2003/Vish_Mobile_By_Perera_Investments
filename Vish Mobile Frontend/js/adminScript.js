$(document).ready(function() {
  // Check if user is logged in
  const isLoggedIn = localStorage.getItem('isLoggedIn');
  if (isLoggedIn === 'true') {
    showMainApp();
  }

  // Tab switching
  $('.auth-tab').click(function() {
    const tabId = $(this).data('tab');

    // Update tabs
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
          // Save JWT token to localStorage
          localStorage.setItem("token", response.token);
          localStorage.setItem("email", response.email);
          localStorage.setItem("username", response.username);
          localStorage.setItem("userId", response.id);
          localStorage.setItem("contactNumber", response.contactNumber);
          localStorage.setItem("address", response.address);
          localStorage.setItem("isLoggedIn", "true");

          showMainApp();
          // Show success notification
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




$(document).ready(function () {
  // Trigger the file input when the "Select Images" button is clicked
  $("#trigger-upload").click(function () {
    // Trigger the file input to open file dialog
    $("#phone-image").click();
  });

  // Handle the file input change event when the user selects files
  $("#phone-image").on("change", function () {
    let files = this.files;
    console.log("Files selected:", files); // Log files to ensure they're being captured

    if (files.length > 0) {
      console.log("Number of files selected:", files.length);
      for (let i = 0; i < files.length; i++) {
        console.log(`File ${i + 1}:`, files[i].name);
      }
    } else {
      console.log("No files selected.");
    }
  });

  // Handle the "Add Phone" button click
  $("#add-phone-btn").click(async function (event) {
    event.preventDefault(); // Prevent form submission

    let phoneData = {
      model: $("input[placeholder='Enter phone model']").val(),
      imei: $("input[placeholder='Enter IMEI number']").val(),
      capacity: $("input[placeholder='Enter storage capacity']").val(),
      color: $("input[placeholder='Enter color']").val(),
      batteryHealth: $("input[placeholder='Enter battery health %']").val(),
      boughtPrice: $("input[placeholder='Enter bought price']").val(),
      sellingPrice: $("input[placeholder='Enter selling price']").val(),
      photoLinks: [] // To store image URLs after upload
    };

    // Upload images to Cloudinary
    let uploadedImages = await uploadImagesToCloudinary();

    if (uploadedImages.length === 0) {
      alert("Please upload at least one image.");
      return;
    }

    phoneData.photoLinks = uploadedImages;

    // Send phone details to the backend
    $.ajax({
      url: "http://localhost:8080/api/v1/sellingPhone/save",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(phoneData),
      success: function (response) {
        alert("Phone added successfully!");
        location.reload(); // Reload to show new phone in the list
      },
      error: function (error) {
        console.error("Error adding phone:", error);
        alert("Failed to add phone. Try again.");
      }
    });
  });

  // Function to upload images to Cloudinary
  async function uploadImagesToCloudinary() {
    let imageInput = $("#phone-image")[0];
    let imageFiles = imageInput.files;
    let imageUrls = [];

    console.log("Selected files:", imageFiles); // Debugging: Log selected files

    if (imageFiles.length === 0) {
      console.log("No images selected."); // Debugging: Log if no files are selected
      return imageUrls;
    }

    let cloudinaryUploadUrl = "https://api.cloudinary.com/v1_1/duxanz6pf/image/upload";
    let cloudinaryUploadPreset = "Phone photos"; // Ensure this is correct in Cloudinary settings

    for (let file of imageFiles) {
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
          dataType: "json" // Ensure response is treated as JSON
        });

        console.log("Cloudinary response:", response); // Debugging: Log Cloudinary response

        if (response.secure_url) {
          imageUrls.push(response.secure_url);
        } else {
          console.error("Unexpected Cloudinary response:", response);
          alert("Image upload failed. Please check your Cloudinary settings.");
        }
      } catch (error) {
        console.error("Image upload failed:", error);
        alert("Image upload failed. Please try again.");
        return []; // Exit early on error
      }
    }

    return imageUrls;
  }
});


$(document).ready(function () {
  const maxImages = 5;
  const imagePreviewContainer = $("#image-preview-container");
  const uploadPlaceholder = $("#upload-placeholder");
  let imageCount = 0;

  $("#phone-image").change(function (event) {
    const files = event.target.files;
    const remainingSlots = maxImages - imageCount;

    if (files.length > remainingSlots) {
      alert(`You can only upload ${remainingSlots} more images.`);
      return;
    }

    Array.from(files).forEach((file) => {
      if (imageCount < maxImages) {
        const reader = new FileReader();
        reader.onload = function (e) {
          const imageHtml = `
                        <div class="image-preview-item">
                            <img src="${e.target.result}" alt="Phone Image" />
                            <div class="image-preview-overlay">
                                <button class="delete-image" onclick="deleteImage(this)">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        </div>`;
          uploadPlaceholder.before(imageHtml);
          imageCount++;
          updateUploadUI();
        };
        reader.readAsDataURL(file);
      }
    });

    // Reset input field to allow re-uploading same file
    $(this).val('');
  });

  window.deleteImage = function (element) {
    $(element).closest(".image-preview-item").remove();
    imageCount--;
    updateUploadUI();
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
      }
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
  let row = $(this).closest("tr"); // Get the row where the button was clicked

  // Extract data from table row
  let model = row.find("td:eq(1)").text().trim();
  let imei = row.find("td:eq(2)").text().trim();
  let capacity = row.find("td:eq(3)").text().trim();
  let color = row.find("td:eq(4)").text().trim();
  let batteryHealth = row.find("td:eq(5)").text().trim().replace('%', '');
  let boughtPrice = row.find("td:eq(6)").text().trim().replace('$', '');
  let sellingPrice = row.find("td:eq(7)").text().trim().replace('$', '');

  // Populate input fields with row data
  $(".phone-detail-form input:eq(0)").val(model);
  $(".phone-detail-form input:eq(1)").val(imei);
  $(".phone-detail-form input:eq(2)").val(capacity);
  $(".phone-detail-form input:eq(3)").val(color);
  $(".phone-detail-form input:eq(4)").val(batteryHealth);
  $(".phone-detail-form input:eq(5)").val(boughtPrice);
  $(".phone-detail-form input:eq(6)").val(sellingPrice);

  // Change "Add Phone" button to "Update Phone"
  $("#add-phone-btn").text("Update Phone").attr("id", "update-phone-btn");

  // update eken nawattuwe methana button eke id eka wenas wena thanata wenakn kara ...
  // karanna thiyenne photo tika load karala ekath update karanna puluwen wenna hadana eka
});
