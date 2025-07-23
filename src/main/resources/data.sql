
-- ===================
-- PRODUCTOS
-- ===================
INSERT INTO producto (
  id,
  nombre,
  descripcion,
  precio,
  categoria,
  imagen_url,
  stock
)
VALUES
  (
    1,
    'Notebook Lenovo',
    'Notebook Lenovo i5 8GB RAM',
    350000,
    'Tecnología',
    'https://images.unsplash.com/photo-1610465299991-994ef9454c51?fit=crop&w=600&q=80',
    10
  ),
  (
    2,
    'Smartphone Samsung',
    'Samsung Galaxy S21',
    280000,
    'Tecnología',
    'https://images.unsplash.com/photo-1611255558567-0c1fdfb6a185?fit=crop&w=600&q=80',
    15
  ),
  (
    3,
    'Auriculares Bluetooth',
    'Auriculares inalámbricos con cancelación de ruido',
    45000,
    'Accesorios',
    'https://images.unsplash.com/photo-1606813903290-d1b20715e90b?fit=crop&w=600&q=80',
    20
  ),
  (
    4,
    'Mouse Gamer',
    'Mouse óptico RGB 16000DPI',
    25000,
    'Accesorios',
    'https://images.unsplash.com/photo-1593642532973-d31b6557fa68?fit=crop&w=600&q=80',
    25
  );
