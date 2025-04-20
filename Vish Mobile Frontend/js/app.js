// ------------------------------NAVIGATION---------------------
$(document).ready(function () {
  $('.page').hide();
  $('.page.active').show();
  $('.nav-link').click(function (e) {
    e.preventDefault();
    const sectionId = $(this).attr('href').substring(1);
    $('.nav-link').removeClass('active');
    $(this).addClass('active');
    $('.page').removeClass('active').hide();
    $('#' + sectionId).addClass('active').fadeIn();
  });
  $('.profile-btn').click(function () {
    let currentPage = $('.page.active').attr('id');
    localStorage.setItem("previousPage", currentPage);
    $('.page').removeClass('active').hide();
    const token = localStorage.getItem("token");
    const currentUser = localStorage.getItem("userId");
    if (
      token && token !== "undefined" &&
      currentUser && currentUser !== "undefined"
    ) {
      $('#my-profile-inside').addClass('active').fadeIn();
      getUserTradingPhones();
    } else {
      $('#my-profile-register').addClass('active').fadeIn();
      $("#signup-section").show();
      $("#login-section").hide();
    }
  });

  $('.cart-btn').click(function () {
    $('.page').removeClass('active').hide();
    $('#my-cart-main').addClass('active').fadeIn();
  });

  $("#show-login").click(function () {
    $("#signup-section").fadeOut(300, function () {
      $("#login-section").fadeIn(300);
    });
  });

  $("#show-signup").click(function () {
    $("#login-section").fadeOut(300, function () {
      $("#signup-section").fadeIn(300);
    });
  });
});

// ---------------------------------Auth--------------------------------------------
$(document).ready(function () {
  $(".auth-tab").click(function () {
    const target = $(this).data("tab");
    $(".auth-tab").removeClass("active");
    $(this).addClass("active");

    $(".auth-page").removeClass("active");
    $("#" + target).addClass("active");
  });

  $("#to-login").click(function (e) {
    e.preventDefault();
    $(".auth-tab[data-tab='login']").click();
  });

  // ------------------------------------Signup--------------------------------------
  $("#to-signup").click(function (e) {
    e.preventDefault();
    $(".auth-tab[data-tab='signup']").click();
  });

  $(".toggle-password").click(function () {
    const targetId = $(this).data("target");
    const input = $("#" + targetId);
    const icon = $(this).find("i");

    if (input.attr("type") === "password") {
      input.attr("type", "text");
      icon.removeClass("fa-eye").addClass("fa-eye-slash");
    } else {
      input.attr("type", "password");
      icon.removeClass("fa-eye-slash").addClass("fa-eye");
    }
  });

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
    if (password.length >= 8) {
      $('.length-check').addClass('valid').removeClass('invalid');
      $('.length-check i').removeClass('fa-times-circle').addClass('fa-check-circle');
    } else {
      $('.length-check').removeClass('valid').addClass('invalid');
      $('.length-check i').removeClass('fa-check-circle').addClass('fa-times-circle');
    }
    if (/[A-Z]/.test(password)) {
      $('.uppercase-check').addClass('valid').removeClass('invalid');
      $('.uppercase-check i').removeClass('fa-times-circle').addClass('fa-check-circle');
    } else {
      $('.uppercase-check').removeClass('valid').addClass('invalid');
      $('.uppercase-check i').removeClass('fa-check-circle').addClass('fa-times-circle');
    }
    if (/\d/.test(password)) {
      $('.number-check').addClass('valid').removeClass('invalid');
      $('.number-check i').removeClass('fa-times-circle').addClass('fa-check-circle');
    } else {
      $('.number-check').removeClass('valid').addClass('invalid');
      $('.number-check i').removeClass('fa-check-circle').addClass('fa-times-circle');
    }
    if (/[^A-Za-z0-9]/.test(password)) {
      $('.special-check').addClass('valid').removeClass('invalid');
      $('.special-check i').removeClass('fa-times-circle').addClass('fa-check-circle');
    } else {
      $('.special-check').removeClass('valid').addClass('invalid');
      $('.special-check i').removeClass('fa-check-circle').addClass('fa-times-circle');
    }
  });

  // -------------------------------REGISTER FORM-------------------------------
  $("#signup-form").submit(function (e) {
    e.preventDefault();
    let valid = true;

    if ($("#username").val().trim() === "") {
      $("#name-error").show();
      valid = false;
    } else {
      $("#name-error").hide();
    }

    const email = $("#email").val().trim();
    if (!/^\S+@\S+\.\S+$/.test(email)) {
      $("#email-error").show();
      valid = false;
    } else {
      $("#email-error").hide();
    }

    if ($(".password-validation .invalid").length > 0) {
      $("#password-error").show();
      valid = false;
    } else {
      $("#password-error").hide();
    }

    const contact = $("#contact_number").val().trim();
    if (!/^[0-9]{10,15}$/.test(contact)) {
      $("#contact-error").show();
      valid = false;
    } else {
      $("#contact-error").hide();
    }

    if ($("#address").val().trim() === "") {
      $("#address-error").show();
      valid = false;
    } else {
      $("#address-error").hide();
    }

    if (!$("#terms").is(":checked")) {
      alert("You must agree to the Terms of Service and Privacy Policy.");
      valid = false;
    }

    if (valid) {
      let userData = {
        username: $("#username").val(),
        email: $("#email").val(),
        password: $("#password").val(),
        contactNumber: $("#contact_number").val(),
        address: $("#address").val(),
        userType: "customer",
        status:"ACTIVE"
      };
      $.ajax({
        url: "http://localhost:8080/api/v1/user/auth/register",
        type: "POST",
        beforeSend: showLoader,
        contentType: "application/json",
        data: JSON.stringify(userData),
        success: function (response) {
          if (response.token != null) {
            resetLocalStorage();
            localStorage.setItem("token", response.token);
            localStorage.setItem("userId", response.userId);
            localStorage.setItem("username", response.username);
            localStorage.setItem("email", response.email);

            let previousPage = localStorage.getItem("previousPage") || "home";

            $('.page').removeClass('active').hide();
            $('#' + previousPage).addClass('active').fadeIn();

            $('.nav-link').removeClass('active');
            $('.nav-link[href="#' + previousPage + '"]').addClass('active');

            alert("User registered successfully!");
          } else {
            console.log(response.message);
            let errMsg = response.message || "Registration failed. Please try again.";
            alert("Registration failed: " + errMsg);
          }
        },
        error: function (xhr, status, error) {
          let errMsg = xhr.responseJSON ? xhr.responseJSON.message : "Unknown error";
          alert("Registration failed: " + errMsg);
        },
        complete:hideLoader
      });
    } else {
      alert("Fill all the fields correctly")
    }
  });

  // ---------------------------------------login------------------------------------
  $("#login-form").submit(function (e) {
    e.preventDefault();
    let valid = true;

    const email = $("#login-email").val().trim();
    if (!/^\S+@\S+\.\S+$/.test(email)) {
      $("#login-email-error").show();
      valid = false;
    } else {
      $("#login-email-error").hide();
    }

    if ($("#login-password").val().trim() === "") {
      $("#login-password-error").text("Please enter your password").show();
      valid = false;
    } else {
      $("#login-password-error").hide();
    }

    if (valid) {
      let authDTO = {
        email: $("#login-email").val(),
        password: $("#login-password").val(),
      };
      $.ajax({
        url: "http://localhost:8080/api/v1/user/auth/login",
        type: "POST",
        beforeSend: showLoader,
        contentType: "application/json",
        data: JSON.stringify(authDTO),
        success: function (response) {
          if (response != null && response.statusCode === 200) {
            const data = response.data;
            resetLocalStorage();
            localStorage.setItem("token", data.token);
            localStorage.setItem("userId", data.user.id);
            localStorage.setItem("username", data.user.username);
            localStorage.setItem("email", data.user.email);
          } else {
            alert("login failed")
          }
          let previousPage = localStorage.getItem("previousPage") || "home";

          $('.page').removeClass('active').hide();
          $('#' + previousPage).addClass('active').fadeIn();

          $('.nav-link').removeClass('active');
          $('.nav-link[href="#' + previousPage + '"]').addClass('active');
        },
        error: function (xhr, status, error) {
          let errMsg = xhr.responseJSON ? xhr.responseJSON.message : "Unknown error";
          alert("Registration failed: " + errMsg);
        },
        complete:hideLoader
      });
    }
  });
});


function resetLocalStorage() {
  localStorage.removeItem("batteryReducedAmount");
  localStorage.removeItem("bestPhonePrice");
  localStorage.removeItem("boxAvailability");
  localStorage.removeItem("box_available");
  localStorage.removeItem("contactNumber");
  localStorage.removeItem("customerTradePhoneId");
  localStorage.removeItem("frameReduceAmount");
  localStorage.removeItem("willingTo");
  localStorage.removeItem("token");
  localStorage.removeItem("userId");
  localStorage.removeItem("username");
  localStorage.removeItem("email");
}

function showNotification(message) {
  $('#notification').text(message);
  $('#notification').fadeIn();

  setTimeout(function() {
    $('#notification').fadeOut();
  }, 3000);
}


// PAGINATION IN BUY PAGE
// $(document).ready(function () {
//   const phonesPerPage = 8; // 5 cards per row × 4 rows = 20 per page
//   let currentPage = 1;
//   let phoneData = []; // Array to hold phone items
//
//   // Dummy data for testing (Replace with actual data fetching)
//   for (let i = 1; i <= 100; i++) {
//     phoneData.push({ id: i, name: "Phone " + i });
//   }
//
//   function renderPhones() {
//     const start = (currentPage - 1) * phonesPerPage;
//     const end = start + phonesPerPage;
//     const currentPhones = phoneData.slice(start, end);
//
//     $(".phone-container").html(""); // Clear previous content
//
//     currentPhones.forEach((phone) => {
//       $(".phone-container").append(`
//         <div class="sale-phone-card">
//           <h3>${phone.name}</h3>
//           <p>Phone details here...</p>
//         </div>
//       `);
//     });
//
//     // Update page number
//     $("#pageNumber").text("Page " + currentPage);
//
//     // Disable/Enable pagination buttons
//     $("#prevPage").prop("disabled", currentPage === 1);
//     $("#nextPage").prop("disabled", end >= phoneData.length);
//   }
//
//   // Pagination Button Events
//   $("#nextPage").click(function () {
//     currentPage++;
//     renderPhones();
//   });
//
//   $("#prevPage").click(function () {
//     currentPage--;
//     renderPhones();
//   });
//
//   // Initial render
//   renderPhones();
// });

// ------------------------------COURSEL MANAGEMENT----------------------------
$(document).ready(function () {
  let $list = $(".list");
  let $items = $(".item");
  let currentIndex = 0;
  let autoSlideInterval;

  function initializeCarousel() {
    let $firstItem = $items.first().clone();
    $list.append($firstItem);
    $items = $(".item");
  }

  function updateCarousel() {
    $list.css("transform", `translateX(-${currentIndex * 100}%)`);
  }

  function nextSlide() {
    currentIndex++;
    if (currentIndex === $items.length) {
      currentIndex = 0;

      $list.css("transition", "none");
      updateCarousel();
      setTimeout(() => {
        $list.css("transition", "transform 0.5s ease");
      }, 50);
    } else {
      updateCarousel();
    }
  }

  function startAutoSlide() {
    autoSlideInterval = setInterval(nextSlide, 3000);
  }

  function stopAutoSlide() {
    clearInterval(autoSlideInterval);
  }

  $list.on("mouseenter", stopAutoSlide);
  $list.on("mouseleave", startAutoSlide);

  initializeCarousel();
  updateCarousel();
  startAutoSlide();
});


$(document).ready(function () {
  $("#show-login").click(function () {
    $("#signup-section").fadeOut(300, function () {
      $("#login-section").fadeIn(300);
    });
  });
  $("#show-signup").click(function () {
    $("#login-section").fadeOut(300, function () {
      $("#signup-section").fadeIn(300);
    });
  });
});


// -------------------------- TRADE PAGE JS-----------------------------------


const maxImages = 5;
let imageCount = 0;
let selectedFiles = [];

const uploadCountText = $("#upload-count-text");
const uploadPlaceholder = $(".upload-placeholder");
const imagePreviewContainer = $(".image-preview-container");

$("#phone-image").change(function (event) {
  const files = Array.from(event.target.files);
  const remainingSlots = maxImages - imageCount;

  if (files.length > remainingSlots) {
    alert(`You can only upload ${remainingSlots} more images.`);
    return;
  }

  files.forEach((file, index) => {
    if (imageCount < maxImages) {
      const reader = new FileReader();
      reader.onload = function (e) {
        const imageHtml = `
        <div class="image-preview-item" data-index="${selectedFiles.length}">
          <img src="${e.target.result}" alt="Phone Image" />
          <div class="image-preview-overlay">
            <button class="delete-image" type="button">
              <i class="fas fa-trash"></i>
            </button>
          </div>
        </div>`;
        imagePreviewContainer.prepend(imageHtml);
        selectedFiles.push(file);
        imageCount++;
        updateUploadUI();
      };
      reader.readAsDataURL(file);
    }
  });

  $(this).val("");
});

$(document).on("click", ".delete-image", function () {
  const item = $(this).closest(".image-preview-item");
  const index = item.data("index");

  selectedFiles.splice(index, 1);
  item.remove();
  imageCount--;

  $(".image-preview-item").each((i, el) => {
    $(el).attr("data-index", i);
  });

  updateUploadUI();
});

function updateUploadUI() {
  uploadCountText.text(`${imageCount}/5 uploaded`);
  if (imageCount >= maxImages) {
    uploadPlaceholder.hide();
  } else {
    uploadPlaceholder.show();
  }
}


async function uploadImagesToCloudinary() {
  if (selectedFiles.length === 0) return [];

  const cloudinaryUploadUrl = "https://api.cloudinary.com/v1_1/duxanz6pf/image/upload";
  const cloudinaryUploadPreset = "phone_photos";

  const imageUrls = [];

  for (let file of selectedFiles) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("upload_preset", cloudinaryUploadPreset);

    try {
      const response = await $.ajax({
        url: cloudinaryUploadUrl,
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json",
      });

      if (response.secure_url) {
        imageUrls.push(response.secure_url);
      }
    } catch (error) {
      console.error("Upload error:", error);
      alert("Failed to upload image.");
    }
  }
  return imageUrls;
}

$(document).ready(function () {
  $(".trade-phone-submit-btn").click(function (event) {
    event.preventDefault();

    const model = $("#model").val();
    const storage = $("#storage").val();
    const batteryHealth = $("#battery_health").val();
    const batteryRange = getBatteryRangeFromPercentage(batteryHealth);
    const frameCondition = $("#frame_condition").val();
    const color = $("#colour").val();
    const willingTo = $("#willing_to").val();
    const boxValue = $('input[name="box_available"]:checked').val();
    const approval = "PENDING";

    if (!validateForm(model, storage, batteryHealth, frameCondition, color, willingTo, boxValue)) return;

    const box = boxValue === "yes" ? "AVAILABLE" : "NOT_AVAILABLE"; // enum compatible
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    if (!token) {
      alert("No token found. Please login.");
      return;
    }

    getPricePrediction(model, storage, batteryRange, frameCondition, token)
      .then(({ phonePrice, batteryReduceAmount, frameReduceAmount }) => {
        const finalPrice = calculateFinalPrice(phonePrice, batteryReduceAmount, frameReduceAmount, box);
        renderPriceBreakdown(model, storage, phonePrice, batteryReduceAmount, frameReduceAmount, box, finalPrice);

        $(".trade-output-submit-seller").off("click").on("click", function (e) {
          e.preventDefault();

          const customerIphone = {
            model,
            storage,
            batteryHealth: batteryRange,
            frameCondition,
            colour: color,
            willingTo,
            box,
            userId,
            approval
          };

          $.ajax({
            url: `http://localhost:8080/api/v1/customerPhoneTrade/save`,
            type: "POST",
            contentType: "application/json",
            headers: { "Authorization": "Bearer " + token },
            data: JSON.stringify(customerIphone),
            success: async function (response) {
              if (response?.statusCode === 200) {
                const phoneId = response.data;
                localStorage.setItem("customerTradePhoneId", phoneId);
                console.log("Trade Phone Saved:", phoneId);

                const uploadedImageUrls = await uploadImagesToCloudinary();

                if (uploadedImageUrls.length > 0) {
                  const photoObjects = uploadedImageUrls.map(url => ({
                    phoneId,
                    photoUrl: url
                  }));

                  $.ajax({
                    url: `http://localhost:8080/api/v1/sellingPhonePhoto/savePhoto`,
                    type: "POST",
                    contentType: "application/json",
                    headers: { "Authorization": "Bearer " + token },
                    data: JSON.stringify(photoObjects),
                    success: function (res) {
                      if (res?.statusCode === 200) {
                        alert("Your phone was sent to the admin successfully.");

                        $('form')[0].reset();
                        $('.image-preview-container').html(`
                            <div class="upload-placeholder" onclick="document.getElementById('phone-image').click();">
                              <i class="fas fa-plus" style="font-size: 24px;"></i>
                            </div>
                        `);
                        $('#upload-count-text').text('0/5 uploaded');

                        $('.profile-btn').trigger('click');
                      } else {
                        alert("Failed to send phone to admin");
                      }
                    },
                    error: function (err) {
                      console.error("Save photo error:", err);
                      alert("Error saving photos");
                    }
                  });
                }
              }
            }
          });
        });
      })
      .catch(err => console.error("Prediction error:", err));
  });

  function validateForm(model, storage, battery, frame, color, willing, box) {
    if (!model || !storage || !battery || !frame || !color || !willing || !box) {
      alert("Please fill all required fields before submitting.");
      return false;
    }
    return true;
  }

  function getBatteryRangeFromPercentage(percentage) {
    const battery = parseInt(percentage.replace("%", "").trim());
    if (battery >= 95 && battery <= 100) return "100-95%";
    else if (battery >= 85) return "95-85%";
    else if (battery >= 80) return "85-80%";
    else if (battery >= 70) return "80-70%";
    else return "below 70%";
  }

  function getPricePrediction(model, storage, batteryRange, frameCondition, token) {
    return new Promise((resolve, reject) => {
      $.ajax({
        url: `http://localhost:8080/api/v1/customerPhonePricePrediction/getPhonePrice?model=${encodeURIComponent(model)}&storage=${encodeURIComponent(storage)}`,
        type: "GET",
        headers: { "Authorization": "Bearer " + token },
        success: function (res1) {
          if (res1?.statusCode !== 200) return reject("Phone price failed");

          const phonePrice = parseInt(res1.data);

          $.ajax({
            url: `http://localhost:8080/api/v1/customerPhonePricePrediction/getBatteryNegotiation?model=${encodeURIComponent(model)}&batteryHealth=${encodeURIComponent(batteryRange)}`,
            type: "GET",
            headers: { "Authorization": "Bearer " + token },
            success: function (res2) {
              if (res2?.statusCode !== 200) return reject("Battery reduction failed");

              const batteryReduceAmount = parseInt(res2.data);

              $.ajax({
                url: `http://localhost:8080/api/v1/customerPhonePricePrediction/getFrameNegotiation?model=${encodeURIComponent(model)}&frameCondition=${encodeURIComponent(frameCondition)}`,
                type: "GET",
                headers: { "Authorization": "Bearer " + token },
                success: function (res3) {
                  if (res3?.statusCode !== 200) return reject("Frame reduction failed");

                  const frameReduceAmount = parseInt(res3.data);
                  resolve({ phonePrice, batteryReduceAmount, frameReduceAmount });
                },
                error: reject,
              });
            },
            error: reject,
          });
        },
        error: reject,
      });
    });
  }
  function calculateFinalPrice(phonePrice, batteryReduceAmount, frameReduceAmount, box) {
    const boxBonus = box === "AVAILABLE" ? 1000 : 0;
    return phonePrice - batteryReduceAmount - frameReduceAmount + boxBonus;
  }
  function renderPriceBreakdown(model, storage, phonePrice, batteryReduce, frameReduce, box, finalPrice) {
    $(".trade-output-inside-body-iphone-model h3").text(`iPhone ${model} ${storage}`);
    $(".trade-output-inside-body-fixed-price").text(`Best price : LKR ${phonePrice.toLocaleString()}.00`);
    $(".battery-negotiation .negotiation-price").text(`- LKR ${batteryReduce.toLocaleString()}`);
    $(".frame-negotiation .negotiation-price").text(`- LKR ${frameReduce.toLocaleString()}`);
    $(".box-negotiation .negotiation-price").text(box === "AVAILABLE" ? "+ LKR 1000" : "LKR 0");
    $(".trade-output-inside-body-current-price .negotiation-price-final").text(`LKR ${finalPrice.toLocaleString()}.00`);
  }
});


// --------------------------------- PROFILE MANAGEMENT JS---------------------------------
let userId = localStorage.getItem("userId");
let token = localStorage.getItem("token");

function getUserTradingPhones() {
  $.ajax({
    url: `http://localhost:8080/api/v1/profileManager/getTradingPhonesByUser/${userId}`,
    type: "GET",
    headers: { "Authorization": "Bearer " + token },
    success: function (response) {
      const container = $("#user-phone-list");
      container.empty();

      if (response?.statusCode !== 200 || !response.data || response.data.length === 0) {
        const card = `<div class="card">
                    <p>Haven't traded any phone</p>
                  </div>`;
        container.append(card);
        return;
      }

      const phoneList = response.data;
      container.empty();

      phoneList.forEach(phone => {
        const card = `
          <div class="user-item-card">
            <div class="user-item-image">
                ${
                    phone.photoUrls && phone.photoUrls.length > 0
                      ? `<img src="${phone.photoUrls[0]}" alt="${phone.model}">`
                      : `<div class="no-photo-msg">No photos are available</div>`
                }
            </div>
            <div class="user-item-details">
              <div class="user-item-title">${phone.model || 'Unknown Model'}</div>
              <div class="user-item-description">
                <div class="user-item-description-group-inside">
                  <div class="user-item-description-group-inside-key">Storage :</div>
                  <div class="user-item-description-group-inside-value">${phone.storage || 'N/A'}</div>
                </div>
                <div class="user-item-description-group-inside">
                  <div class="user-item-description-group-inside-key">Battery health :</div>
                  <div class="user-item-description-group-inside-value">${phone.batteryHealth || 'N/A'}</div>
                </div>
                <div class="user-item-description-group-inside">
                  <div class="user-item-description-group-inside-key">Frame :</div>
                  <div class="user-item-description-group-inside-value">${phone.frameCondition || 'N/A'}</div>
                </div>
                <div class="user-item-description-group-inside">
                  <div class="user-item-description-group-inside-key">Color :</div>
                  <div class="user-item-description-group-inside-value">${phone.colour || 'N/A'}</div>
                </div>
                <div class="user-item-description-group-inside">
                  <div class="user-item-description-group-inside-key">Box :</div>
                  <div class="user-item-description-group-inside-value">${formatBoxValue(phone.box)}</div>
                </div>
                <div class="user-item-description-group-inside">
                  <div class="user-item-description-group-inside-key">Status :</div>
                  <div class="user-item-description-group-inside-value">${formatApprovalStatus(phone.approval)}</div>
                </div>
              </div>
              <div class="user-item-buttons">
                <button class="user-item-buy">Buy</button>
                <button class="user-item-remove">Remove item</button>
              </div>
            </div>
          </div>
        `;

        container.append(card);
      });
    },
    error: function (err) {
      console.error("Error fetching trading phones:", err);
    }
  });
}

// Optional: Format 'AVAILABLE' to 'Available' etc.
function formatBoxValue(value) {
  if (!value) return "Not Available";
  return value.replace("_", " ").toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
}
function formatApprovalStatus(status) {
  if (!status) return 'N/A';
  const lower = status.toLowerCase();
  return lower.charAt(0).toUpperCase() + lower.slice(1); // e.g., "APPROVED" => "Approved"
}

function showLoader() {
  const overlay = document.getElementById("loading-overlay");
  overlay.style.display = "flex";
}

function hideLoader() {
  const overlay = document.getElementById("loading-overlay");
  overlay.style.display = "none";
}
$(document).ready(function () {
  $(".log-out").on("click", function () {
    resetLocalStorage();
    window.location.reload();
  });
});



