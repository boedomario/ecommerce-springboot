document.addEventListener("DOMContentLoaded", () => {
  fetch("/api/productos")
    .then((response) => {
      if (!response.ok) throw new Error("Error al obtener productos");
      return response.json();
    })
    .then((productos) => {
      cargarProductos(productos); // productos es un array
    })
    .catch((error) =>
      console.error("Error al obtener productos desde el backend", error)
    );

  function cargarProductos(productos) {
    const productosContainer = document.getElementById("productos-container");
    productosContainer.innerHTML = "";

    productos.forEach((producto) => {
      const shortDescription =
        producto.descripcion?.split(" ").slice(0, 5).join(" ") + "..." || "";

      // Si el stock es bajo, agregamos advertencia visual
      const stockAlerta =
        producto.stock < 5
          ? `<p class="text-danger"><strong>¡Stock bajo!</strong> (${producto.stock} unidades)</p>`
          : "";

      productosContainer.innerHTML += `
      <div class="card ${producto.stock < 5 ? "border border-danger" : ""}">
        <img src="${producto.imagenUrl || "default-image.png"}"
             class="card-img-top"
             alt="${producto.nombre}">
        <div class="card-body">
          <h5 class="card-title">${producto.nombre}</h5>
          <p class="card-text short-description">${shortDescription}</p>
          <p class="card-text full-description" style="display: none;">
            ${producto.descripcion || ""}
          </p>
          <button class="btn btn-link" onclick="toggleDescription(this)">
            Ver descripción
          </button>
          <p class="card-text">$${producto.precio.toFixed(2)}</p>
          ${stockAlerta}
          <button class="btn btn-primary" onclick="addToCart(${producto.id}, '${
        producto.imagenUrl
      }', '${producto.nombre}', ${producto.precio}, this)">
            Agregar al carrito
          </button>
        </div>
      </div>
    `;
    });
  }

  // barra de busqyeda por nombre
  document.getElementById("btn-buscar").addEventListener("click", () => {
    const termino = document.getElementById("busqueda-input").value.trim();
    if (termino === "") return;

    fetch(`/api/productos/buscar?nombre=${encodeURIComponent(termino)}`)
      .then((res) => {
        if (!res.ok) throw new Error("Error al buscar productos");
        return res.json();
      })
      .then((productos) => {
        cargarProductos(productos);
      })
      .catch((err) => {
        console.error("Error en la búsqueda:", err);
        alert("No se pudieron buscar productos.");
      });
  });

  // permite buscarcon Enter
  document
    .getElementById("busqueda-input")
    .addEventListener("keypress", (e) => {
      if (e.key === "Enter") {
        document.getElementById("btn-buscar").click();
      }
    });

  // para usuarios
  document.getElementById("registrar-usuario").addEventListener("click", () => {
    const nombre = document.getElementById("nombre").value.trim();
    const email = document.getElementById("email").value.trim();

    if (!nombre || !email) {
      alert("Completá ambos campos");
      return;
    }

    fetch("/api/usuarios", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nombre, email }),
    })
      .then((res) => {
        if (!res.ok) throw new Error("Error al registrar");
        return res.json();
      })
      .then((usuario) => {
        localStorage.setItem("usuarioId", usuario.id);
        alert("Registrado correctamente. ID: " + usuario.id);
      })
      .catch((err) => {
        alert("Error al registrar: " + err.message);
      });
  });

  window.addToCart = function (id, image, nombre, precio, button) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    const existing = cart.find((p) => p.id === id);
    if (existing) {
      existing.quantity++;
    } else {
      cart.push({ id, image, title: nombre, price: precio, quantity: 1 });
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    updateCartUI();

    button.textContent = "Agregado";
    button.disabled = true;
    setTimeout(() => {
      button.textContent = "Agregar al carrito";
      button.disabled = false;
    }, 1000);
  };

  window.toggleDescription = function (button) {
    const shortDesc = button.previousElementSibling;
    const fullDesc = shortDesc.nextElementSibling;
    if (fullDesc.style.display === "none") {
      fullDesc.style.display = "block";
      shortDesc.style.display = "none";
      button.textContent = "Ocultar descripción";
    } else {
      fullDesc.style.display = "none";
      shortDesc.style.display = "block";
      button.textContent = "Ver descripción";
    }
  };

  window.removeFromCart = function (id) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    cart = cart.filter((p) => p.id !== id);
    localStorage.setItem("cart", JSON.stringify(cart));
    updateCartUI();
  };

  function updateCartUI() {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    const carritoItems = document.getElementById("carrito-items");
    carritoItems.innerHTML = "";
    let total = 0;

    if (cart.length === 0) {
      carritoItems.innerHTML =
        '<li class="text-center text-muted w-100">Tu carrito está vacío</li>';
      document.getElementById("realizar-compra").disabled = true;
    } else {
      document.getElementById("realizar-compra").disabled = false;
      cart.forEach((item) => {
        carritoItems.innerHTML += `
          <li class="cart-item">
            <img src="${item.image}" alt="${item.title}">
            <div class="card-body">
              <h6 class="card-title">${item.title}</h6>
              <p class="card-text">Cantidad: <strong>${
                item.quantity
              }</strong></p>
              <p class="card-text">Precio unitario: $${item.price}</p>
              <p class="card-text">Subtotal: <strong>$${(
                item.price * item.quantity
              ).toFixed(2)}</strong></p>
              <button class="btn btn-sm btn-danger" onclick="removeFromCart(${
                item.id
              })">Eliminar</button>
            </div>
          </li>
        `;
        total += item.price * item.quantity;
      });
    }

    document.getElementById("carrito-total").textContent = total.toFixed(2);
    document.getElementById("cart-counter").textContent = cart.reduce(
      (sum, item) => sum + item.quantity,
      0
    );
    document.getElementById("cart-counter-nav").textContent = cart.reduce(
      (sum, item) => sum + item.quantity,
      0
    );
  }

  document.getElementById("vaciar-carrito").addEventListener("click", () => {
    localStorage.removeItem("cart");
    updateCartUI();
  });

  document.getElementById("realizar-compra").addEventListener("click", () => {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    if (cart.length === 0) {
      alert("Tu carrito está vacío");
      return;
    }

    //const usuarioId = 1; // hardcoded por ahora
    const usuarioId = localStorage.getItem("usuarioId");
    if (!usuarioId) {
      alert("Debés registrarte antes de comprar.");
      return;
    }

    const pedidoRequest = {
      usuarioId,
      productos: cart.map((item) => ({
        productoId: item.id,
        cantidad: item.quantity,
      })),
    };

    fetch("/api/pedidos", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(pedidoRequest),
    })
      .then((res) => {
        if (!res.ok) {
          return res.text().then((text) => {
            throw new Error(text);
          });
        }
        return res.json();
      })
      .then(() => {
        const modal = new bootstrap.Modal(
          document.getElementById("compraExitosaModal")
        );
        modal.show();
        localStorage.removeItem("cart");
        updateCartUI();
      })
      .catch((err) => {
        alert("Error al realizar la compra: " + err.message);
      });
  });

  updateCartUI();
});
