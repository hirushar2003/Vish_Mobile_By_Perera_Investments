// ------------------------------NAVIGATION---------------------
$(document).ready(function () {
  // Ensure only the active section is visible on page load
  $('.page').hide();
  $('.page.active').show();

  // Navigation Links Click
  $('.nav-link').click(function (e) {
    e.preventDefault();
    const sectionId = $(this).attr('href').substring(1); // Get section ID

    // Update active link
    $('.nav-link').removeClass('active');
    $(this).addClass('active');

    // Show selected section
    $('.page').removeClass('active').hide();
    $('#' + sectionId).addClass('active').fadeIn();
  });

  // Profile Button Click (Opens My Profile Register)
  $('.profile-btn').click(function () {
    $('.page').removeClass('active').hide();
    $('#my-profile-register').addClass('active').fadeIn();

    // Reset login/signup visibility
    $("#signup-section").show();
    $("#login-section").hide();
  });

  // Cart Button Click (Opens My Cart Main)
  $('.cart-btn').click(function () {
    $('.page').removeClass('active').hide();
    $('#my-cart-main').addClass('active').fadeIn();
  });

  // Show login form when clicking "Already have an account?"
  $("#show-login").click(function () {
    $("#signup-section").fadeOut(300, function () {
      $("#login-section").fadeIn(300);
    });
  });

  // Show signup form when clicking "Don't have an account?"
  $("#show-signup").click(function () {
    $("#login-section").fadeOut(300, function () {
      $("#signup-section").fadeIn(300);
    });
  });
});

// ------------------photo upload-------------------------
$(document).ready(function () {
  const maxImages = 5;
  const imagePreviewContainer = $("#image-preview-container");
  const uploadPlaceholder = $("#upload-placeholder");
  const uploadCountText = $("#upload-count-text");
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
                <button class="delete-image">
                  <i class="fas fa-trash"></i>
                </button>
              </div>
            </div>`;
          imagePreviewContainer.prepend(imageHtml); // Add new images before existing ones
          imageCount++;
          updateUploadUI();
        };
        reader.readAsDataURL(file);
      }
    });

    // Reset input field to allow re-uploading the same file
    $(this).val('');
  });

  // Event delegation for dynamically added delete buttons
  $(document).on("click", ".delete-image", function () {
    $(this).closest(".image-preview-item").remove();
    imageCount--;
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
});






















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










$(document).ready(function () {
  let $list = $(".list");
  let $items = $(".item");
  let currentIndex = 0;
  let autoSlideInterval;

  // Clone the first item and append it at the end to create a seamless loop
  function initializeCarousel() {
    let $firstItem = $items.first().clone();
    $list.append($firstItem);
    $items = $(".item"); // Re-select all items after modification
  }

  function updateCarousel() {
    $list.css("transform", `translateX(-${currentIndex * 100}%)`);
  }

  function nextSlide() {
    currentIndex++;
    if (currentIndex === $items.length) {
      currentIndex = 0;
      // Once we reach the cloned item (the last one), reset it without animation
      $list.css("transition", "none");
      updateCarousel();
      // After resetting, enable the transition again
      setTimeout(() => {
        $list.css("transition", "transform 0.5s ease");
      }, 50);
    } else {
      updateCarousel();
    }
  }

  function startAutoSlide() {
    autoSlideInterval = setInterval(nextSlide, 3000); // Change every 2 seconds
  }

  function stopAutoSlide() {
    clearInterval(autoSlideInterval);
  }

  $list.on("mouseenter", stopAutoSlide);
  $list.on("mouseleave", startAutoSlide);

  initializeCarousel(); // Initialize the carousel and make it loopable
  updateCarousel(); // Initialize the first view
  startAutoSlide(); // Start auto sliding
});

$(document).ready(function () {
  $("#signup-form").submit(function (event) {
    event.preventDefault(); // Prevent form from submitting normally

    // Get form data
    let userData = {
      username: $("#username").val(),
      email: $("#email").val(),
      password: $("#password").val(),
      contactNumber: $("#contact_number").val(),
      address: $("#address").val(),
      userType: "customer" // Set userType in frontend
    };

    // Send AJAX request
    $.ajax({
      url: "http://localhost:8080/api/v1/user/auth/register",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(userData),
      success: function (response) {
        alert("User registered successfully!");
        console.log("Success:", response);
        // Redirect to login page if needed
        window.location.href = "../pages/admin.html";
      },
      error: function (xhr, status, error) {
        let errMsg = xhr.responseJSON ? xhr.responseJSON.message : "Unknown error";
        alert("Registration failed: " + errMsg);
        console.error("Error:", xhr.responseText);
      }
    });
  });
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


