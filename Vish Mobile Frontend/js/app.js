let header = $(".header");
let home = $(".home");
let buy = $(".buy");

header.css("display", "block");
home.css("display", "block");
buy.css("display", "none");

$(".homeJs").on("click", function (event) {
  header.css("display", "block");
  home.css("display", "block");
  buy.css("display", "none");
});

$(".buyJs").click(function () {
  header.css("display", "block");
  home.css("display", "none");
  buy.css("display", "block");
});


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


function fetchPhones() {
  $.ajax({
    url: "http://localhost:8080/api/v1/sellingPhone/getAll", // Ensure this endpoint is correct
    method: "GET",
    success: function(response) {
      let phones = response.data; // Ensure this matches API response structure
      let phoneContainer = $(".phone-container");
      phoneContainer.empty(); // Clear existing content

      phones.forEach(phone => {
        let phoneHtml = `
          <div class="phone-item">
              <img src="${phone.photoUrls && phone.photoUrls.length > 0 ? phone.photoUrls[0] : 'default.jpg'}"
                   alt="${phone.model}" width="150">
              <h3>${phone.model} - ${phone.capacity} - ${phone.color}</h3>
              <p>Price: ${phone.sellingPrice}</p>
          </div>
        `;
        phoneContainer.append(phoneHtml); // Append to the correct container
      });
    },
    error: function(error) {
      console.error("Error fetching phones:", error);
    }
  });
}

// Fetch immediately on page load
$(document).ready(fetchPhones);



