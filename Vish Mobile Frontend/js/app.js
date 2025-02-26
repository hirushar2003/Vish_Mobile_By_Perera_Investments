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
