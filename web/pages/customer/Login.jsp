<%-- Document : Login Created on : Feb 19, 2025, 12:53:44 PM Author : nhutt --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <title>Đăng Nhập</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
      rel="stylesheet"
    />
    <style>
      input[type="number"]::-webkit-inner-spin-button,
      input[type="number"]::-webkit-outer-spin-button {
        margin: 0;
        -webkit-appearance: none;
      }
    </style>
  </head>
  <body>
    <div class="vh-100 d-flex flex-column">
      
      <nav class="navbar navbar-expand py-lg-4 py-3 w-100 mb-auto px-lg-5">
        <div class="container">
          <a
            class="navbar-brand d-flex align-items-center gap-2"
            href="./Controller?action=Login"
          >
            <i class="bi bi-car-front"></i>
            <span>Dịch Vụ Ô Tô</span>
          </a>
        </div>
      </nav>

      <div class="container m-auto px-lg-5">
        <div class="row align-items-center px-lg-5">
          <div class="col-lg-7 col-12 d-none d-lg-block">
            <h1 class="fw-bold display-4 text-black">
              Dịch vụ chăm sóc xe chuyên nghiệp và đáng tin cậy
            </h1>
            <p class="py-4 lead">
              Garage của chúng tôi cung cấp các giải pháp toàn diện, từ bảo
              dưỡng định kỳ đến sửa chữa chuyên sâu, đảm bảo chiếc xe của bạn
              luôn vận hành an toàn và hiệu quả. Với đội ngũ kỹ thuật viên giàu
              kinh nghiệm và trang thiết bị hiện đại, chúng tôi cam kết mang đến
              sự hài lòng tối đa cho khách hàng.
            </p>

            <a href="#" class="btn btn-danger">Xem thêm</a>
          </div>
          <div class="col-lg-5 col-12 py-3">
            <div class="p-5 bg-white rounded-4 shadow">
              <h5 class="fw-bold mb-4">Đăng Nhập</h5>
              <form
                action="./Controller"
                method="POST"
                accept-charset="UTF-8"
                class="d-grid gap-3"
              >
                <input
                  name="name"
                  type="text"
                  class="form-control bg-light border-0 px-3 py-2"
                  placeholder="Tên khách hàng"
                  required
                />
                <input
                  name="phoneNumber"
                  type="number"
                  class="form-control bg-light border-0 px-3 py-2"
                  placeholder="Số điện thoại"
                  required
                />
                <div class="d-flex justify-content-between align-items-center">
                  <div class="form-check">
                    <input
                      type="checkbox"
                      class="form-check-input"
                      id="rememberMe"
                      name="rememberMe"
                    />
                    <label class="form-check-label" for="rememberMe"
                      >Ghi nhớ đăng nhập</label
                    >
                  </div>
                  
                </div>
                <button
                  type="submit"
                  class="btn btn-danger w-100"
                  name="action"
                  value="Login"
                >
                  Đăng Nhập
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>

      <footer class="bg-light py-3 mt-auto">
        <div class="container d-flex justify-content-between">
          <p class="mb-0 text-muted small"></p>
          <div>
            <a href="#" class="text-secondary me-3"
              ><i class="bi bi-facebook"></i
            ></a>
            <a href="#" class="text-secondary me-3"
              ><i class="bi bi-twitter"></i
            ></a>
            <a href="#" class="text-secondary me-3"
              ><i class="bi bi-instagram"></i
            ></a>
          </div>
        </div>
      </footer>
    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
    <script>
      (function () {
        const nameInput = document.querySelector('input[name="name"]');
        const phoneInput = document.querySelector('input[name="phoneNumber"]');
        const rememberMeCheckbox = document.querySelector(
          'input[name="rememberMe"]'
        );

        if (nameInput && phoneInput && rememberMeCheckbox) {
          const savedUser = JSON.parse(localStorage.getItem("userData")) || {};

          if (savedUser.rememberMe) {
            nameInput.value = savedUser.name || "";
            phoneInput.value = savedUser.phone || "";
            rememberMeCheckbox.checked = true;
          }
          document
            .querySelector("form")
            .addEventListener("submit", function () {
              if (rememberMeCheckbox.checked) {
                const userData = {
                  name: nameInput.value,
                  phone: phoneInput.value,
                  rememberMe: true
                };
                localStorage.setItem("userData", JSON.stringify(userData));
              } else {
                localStorage.removeItem("userData");
              }
            });
        }
      })();
    </script>
  </body>
</html>
