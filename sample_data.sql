-- =======================================================
-- FoodEase CORRECTED Sample Data for MySQL Database
-- Based on actual domain class analysis
-- Run this script in MySQL Navicat to populate your database
-- =======================================================

-- ================================
-- 1. CITIES
-- ================================
INSERT INTO cities (id, name) VALUES
(2, 'Ho Chi Minh City'),
(3, 'Da Nang'),
(4, 'Can Tho'),
(5, 'Hue');

-- ================================
-- 2. CITY DELIVERABILITY
-- ================================
INSERT INTO city_deliverability (city_id, is_deliverable) VALUES
(1, true),
(2, true),
(3, true),
(4, false),
(5, true);

-- ================================
-- 3. ROLES
-- ================================
INSERT INTO roles (id, name, description) VALUES
(3, 'RESTAURANT_OWNER', 'Restaurant owner role');

-- ================================
-- 4. USERS
-- ================================
INSERT INTO users (id, email, password, login, activated, lang_key, created_by, created_at) VALUES
(7, 'john.doe@gmail.com', '$2a$10$9XZm7LHcKnr9p9xG8w8Y6OuK3H8JKNrYzQw8C2xZy7vN8dF5bH2Gi', 'johndoe', true, 'vi', 'system', NOW()),
(2, 'jane.smith@gmail.com', '$2a$10$9XZm7LHcKnr9p9xG8w8Y6OuK3H8JKNrYzQw8C2xZy7vN8dF5bH2Gi', 'janesmith', true, 'vi', 'system', NOW()),
(3, 'restaurant1@foodease.com', '$2a$10$9XZm7LHcKnr9p9xG8w8Y6OuK3H8JKNrYzQw8C2xZy7vN8dF5bH2Gi', 'restaurant1', true, 'vi', 'system', NOW()),
(4, 'restaurant2@foodease.com', '$2a$10$9XZm7LHcKnr9p9xG8w8Y6OuK3H8JKNrYzQw8C2xZy7vN8dF5bH2Gi', 'restaurant2', true, 'vi', 'system', NOW()),
(5, 'mike.wilson@gmail.com', '$2a$10$9XZm7LHcKnr9p9xG8w8Y6OuK3H8JKNrYzQw8C2xZy7vN8dF5bH2Gi', 'mikewilson', true, 'vi', 'system', NOW()),
(6, 'admin@foodease.com', '$2a$10$9XZm7LHcKnr9p9xG8w8Y6OuK3H8JKNrYzQw8C2xZy7vN8dF5bH2Gi', 'admin', true, 'vi', 'system', NOW());

-- ================================
-- 5. USER ROLES (Many-to-Many relationship)
-- ================================
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- John Doe - USER
(2, 1), -- Jane Smith - USER
(3, 3), -- Restaurant 1 Owner
(4, 3), -- Restaurant 2 Owner
(5, 1), -- Mike Wilson - USER
(6, 2); -- Admin

-- ================================
-- 6. USER PROFILES (One-to-One with User)
-- ================================
INSERT INTO user_profiles (id, user_id, full_name, phone, image_url, referral_code, city_id, latitude, longitude) VALUES
(1, 1, 'John Doe', '0901234567', 'https://randomuser.me/api/portraits/men/1.jpg', 'JD2024', 1, 10.7769, 106.7009),
(2, 2, 'Jane Smith', '0907654321', 'https://randomuser.me/api/portraits/women/2.jpg', 'JS2024', 1, 10.7829, 106.6989),
(3, 3, 'Nguyen Van A', '0912345678', 'https://randomuser.me/api/portraits/men/3.jpg', 'NVA2024', 1, 10.7749, 106.7019),
(4, 4, 'Tran Thi B', '0987654321', 'https://randomuser.me/api/portraits/women/4.jpg', 'TTB2024', 2, 21.0285, 105.8542),
(5, 5, 'Mike Wilson', '0934567890', 'https://randomuser.me/api/portraits/men/5.jpg', 'MW2024', 3, 16.0544, 108.2022),
(6, 6, 'Admin User', '0911111111', 'https://randomuser.me/api/portraits/men/6.jpg', 'ADMIN', 1, 10.7769, 106.7009);

-- ================================
-- 7. PROMO CODES
-- ================================
INSERT INTO promo_codes (id, code, discount_percentage, discount_amount, min_order_value, expires_at) VALUES
(1, 'WELCOME20', 20, null, 100000, DATE_ADD(NOW(), INTERVAL 30 DAY)),
(2, 'SAVE50K', null, 50000, 200000, DATE_ADD(NOW(), INTERVAL 15 DAY)),
(3, 'NEWUSER', 15, null, 50000, DATE_ADD(NOW(), INTERVAL 60 DAY));

-- ================================
-- 8. RESTAURANTS
-- ================================
INSERT INTO restaurants (id, name, description, address, phone, email, image_url, rating, delivery_time, is_open, cuisine_types, min_price, max_price, latitude, longitude, opening_hours, open_time, close_time, owner_id) VALUES
(1, 'The Imaginary Eatery', 'Best restaurant in town serving delicious Vietnamese and International cuisine with fresh ingredients and authentic flavors', '123 Nguyen Hue, District 1, Ho Chi Minh City', '+84901234567', 'contact@imaginaryeatery.com', 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800', 4.2, 25, true, '["Vietnamese", "International", "Fast Food"]', 50000, 500000, 10.7769, 106.7009, '{"monday": "9:00 AM - 11:00 PM", "tuesday": "9:00 AM - 11:00 PM", "wednesday": "9:00 AM - 11:00 PM", "thursday": "9:00 AM - 11:00 PM", "friday": "9:00 AM - 11:00 PM", "saturday": "9:00 AM - 11:00 PM", "sunday": "10:00 AM - 10:00 PM"}', '09:00:00', '23:00:00', 3),

(2, 'Saigon Pizza House', 'Authentic Italian pizza with a Vietnamese twist, made with premium ingredients imported from Italy', '456 Le Loi, District 1, Ho Chi Minh City', '+84907654321', 'info@saigonpizza.com', 'https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=800', 4.5, 30, true, '["Italian", "Pizza", "Fast Food"]', 80000, 400000, 10.7829, 106.6989, '{"monday": "11:00 AM - 10:00 PM", "tuesday": "11:00 AM - 10:00 PM", "wednesday": "11:00 AM - 10:00 PM", "thursday": "11:00 AM - 10:00 PM", "friday": "11:00 AM - 11:00 PM", "saturday": "11:00 AM - 11:00 PM", "sunday": "11:00 AM - 10:00 PM"}', '11:00:00', '22:00:00', 4),

(3, 'Pho Corner', 'Traditional Vietnamese pho and noodle dishes made with recipes passed down through generations', '789 Pasteur, District 3, Ho Chi Minh City', '+84912345678', 'hello@phocorner.vn', 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800', 4.7, 20, true, '["Vietnamese", "Noodles", "Traditional"]', 30000, 150000, 10.7749, 106.7019, '{"monday": "6:00 AM - 10:00 PM", "tuesday": "6:00 AM - 10:00 PM", "wednesday": "6:00 AM - 10:00 PM", "thursday": "6:00 AM - 10:00 PM", "friday": "6:00 AM - 10:00 PM", "saturday": "6:00 AM - 10:00 PM", "sunday": "6:00 AM - 10:00 PM"}', '06:00:00', '22:00:00', 3),

(4, 'Burger Station', 'American-style burgers and fries made with 100% premium beef and fresh vegetables', '321 Dong Khoi, District 1, Ho Chi Minh City', '+84987654321', 'contact@burgerstation.vn', 'https://images.unsplash.com/photo-1551782450-a2132b4ba21d?w=800', 4.0, 35, true, '["American", "Burgers", "Fast Food"]', 60000, 300000, 10.7809, 106.7000, '{"monday": "10:00 AM - 11:00 PM", "tuesday": "10:00 AM - 11:00 PM", "wednesday": "10:00 AM - 11:00 PM", "thursday": "10:00 AM - 11:00 PM", "friday": "10:00 AM - 12:00 AM", "saturday": "10:00 AM - 12:00 AM", "sunday": "10:00 AM - 11:00 PM"}', '10:00:00', '23:00:00', 4);

-- ================================
-- 9. MENU CATEGORIES (Note: belongs to restaurant, not global)
-- ================================
INSERT INTO menu_categories (id, restaurant_id, name) VALUES
-- Restaurant 1 categories
(1, 1, 'Pizza'),
(2, 1, 'Grill Sandwich'),
(3, 1, 'Beverages'),
-- Restaurant 2 categories
(4, 2, 'Pizza'),
(5, 2, 'Beverages'),
-- Restaurant 3 categories
(6, 3, 'Vietnamese Noodles'),
(7, 3, 'Traditional Soup'),
-- Restaurant 4 categories
(8, 4, 'Burgers'),
(9, 4, 'Sides'),
(10, 4, 'Beverages');

-- ================================
-- 10. MENU ITEMS
-- ================================
INSERT INTO menu_items (id, restaurant_id, category_id, name, description, price, original_price, discount_percentage, image_url, is_vegetarian, is_available, rating, reviews_count, preparation_time, ingredients, allergens, nutritional_info, spice_level, tags) VALUES

-- Restaurant 1 - The Imaginary Eatery
(1, 1, 1, 'Garden Fresh Pizza', 'Revel in the goodness of nature with a pizza brimming with fresh vegetables, mozzarella cheese, and homemade tomato sauce', 669000, 769000, 13, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=600', true, true, 4.5, 128, 15, '["Cheese", "Tomato", "Bell Peppers", "Mushrooms", "Olives", "Onions", "Spinach"]', '["Gluten", "Dairy"]', '{"calories": 280, "protein": 12, "carbs": 35, "fat": 10, "fiber": 3, "sugar": 5, "sodium": 890}', 'mild', '["popular", "chef-special", "vegetarian"]'),

(2, 1, 1, 'Four Cheese Fantasy', 'Indulge in a symphony of cheeses with a blend of mozzarella, cheddar, parmesan, and blue cheese on our signature crust', 559000, null, 0, 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=600', true, true, 4.8, 95, 18, '["Mozzarella", "Cheddar", "Parmesan", "Blue Cheese", "Tomato Sauce"]', '["Gluten", "Dairy"]', '{"calories": 320, "protein": 18, "carbs": 28, "fat": 18, "fiber": 2, "sugar": 3, "sodium": 950}', 'none', '["cheese-lovers", "premium"]'),

(3, 1, 2, 'Grilled Chicken Sandwich', 'Tender grilled chicken breast with fresh lettuce, tomato, and our special mayo sauce on artisan bread', 189000, 229000, 17, 'https://images.unsplash.com/photo-1553909489-cd47e0ef937f?w=600', false, true, 4.3, 67, 12, '["Chicken Breast", "Lettuce", "Tomato", "Mayo", "Artisan Bread", "Red Onion"]', '["Gluten"]', '{"calories": 380, "protein": 28, "carbs": 32, "fat": 15, "fiber": 4, "sugar": 6, "sodium": 720}', 'mild', '["healthy", "protein-rich"]'),

(4, 1, 3, 'Fresh Orange Juice', 'Freshly squeezed orange juice made from premium Valencia oranges', 45000, null, 0, 'https://images.unsplash.com/photo-1544145945-f90425340c7e?w=600', true, true, 4.6, 42, 3, '["Fresh Oranges"]', '[]', '{"calories": 112, "protein": 2, "carbs": 26, "fat": 0, "fiber": 0, "sugar": 21, "sodium": 2}', 'none', '["fresh", "healthy", "vitamin-c"]'),

-- Restaurant 2 - Saigon Pizza House  
(5, 2, 4, 'Margherita Classic', 'Traditional Italian margherita with fresh basil, mozzarella di bufala, and authentic San Marzano tomato sauce', 329000, null, 0, 'https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=600', true, true, 4.7, 156, 14, '["Tomato Sauce", "Mozzarella di Bufala", "Fresh Basil", "Extra Virgin Olive Oil"]', '["Gluten", "Dairy"]', '{"calories": 250, "protein": 11, "carbs": 31, "fat": 9, "fiber": 2, "sugar": 4, "sodium": 640}', 'none', '["classic", "traditional", "vegetarian"]'),

(6, 2, 4, 'Spicy Pepperoni', 'Loaded with spicy pepperoni, extra mozzarella cheese, and a kick of chili flakes for heat lovers', 449000, 499000, 10, 'https://images.unsplash.com/photo-1628840042765-356cda07504e?w=600', false, true, 4.4, 203, 16, '["Pepperoni", "Mozzarella", "Tomato Sauce", "Chili Flakes", "Oregano"]', '["Gluten", "Dairy"]', '{"calories": 310, "protein": 15, "carbs": 29, "fat": 16, "fiber": 2, "sugar": 5, "sodium": 890}', 'hot', '["spicy", "popular", "meat-lovers"]'),

(7, 2, 5, 'Italian Soda', 'Refreshing Italian soda available in multiple fruit flavors - Orange, Lemon, Cherry', 35000, null, 0, 'https://images.unsplash.com/photo-1530373239216-42518e6b4063?w=600', true, true, 4.2, 28, 2, '["Carbonated Water", "Natural Fruit Flavors", "Cane Sugar"]', '[]', '{"calories": 140, "protein": 0, "carbs": 38, "fat": 0, "fiber": 0, "sugar": 38, "sodium": 35}', 'none', '["refreshing", "carbonated"]'),

-- Restaurant 3 - Pho Corner
(8, 3, 6, 'Pho Bo Tai', 'Traditional beef pho with rare beef slices, served with fresh herbs, bean sprouts, and lime', 89000, null, 0, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600', false, true, 4.8, 312, 18, '["Beef Broth", "Rice Noodles", "Rare Beef", "White Onions", "Cilantro", "Bean Sprouts"]', '["Gluten"]', '{"calories": 350, "protein": 25, "carbs": 45, "fat": 8, "fiber": 3, "sugar": 6, "sodium": 1200}', 'mild', '["traditional", "comfort-food", "signature"]'),

(9, 3, 6, 'Pho Ga', 'Chicken pho with tender chicken pieces, served in our signature chicken broth with fresh herbs', 79000, null, 0, 'https://images.unsplash.com/photo-1588566565463-180a5b2090de?w=600', false, true, 4.6, 245, 16, '["Chicken Broth", "Rice Noodles", "Chicken", "White Onions", "Cilantro", "Ginger"]', '["Gluten"]', '{"calories": 320, "protein": 22, "carbs": 42, "fat": 6, "fiber": 3, "sugar": 5, "sodium": 1000}', 'mild', '["traditional", "light", "healthy"]'),

(10, 3, 7, 'Bun Bo Hue', 'Spicy beef noodle soup from Hue with thick rice noodles, beef, and lemongrass in a rich broth', 95000, null, 0, 'https://images.unsplash.com/photo-1559564484-0b8250207d6a?w=600', false, true, 4.7, 187, 20, '["Beef Broth", "Round Rice Noodles", "Beef Shank", "Pork", "Lemongrass", "Chili Oil"]', '["Gluten"]', '{"calories": 380, "protein": 28, "carbs": 38, "fat": 12, "fiber": 4, "sugar": 7, "sodium": 1400}', 'very_hot', '["spicy", "traditional", "regional-specialty"]'),

-- Restaurant 4 - Burger Station
(11, 4, 8, 'Classic Beef Burger', 'Juicy 6oz beef patty with lettuce, tomato, onion, pickles, and our special house sauce', 159000, null, 0, 'https://images.unsplash.com/photo-1551782450-a2132b4ba21d?w=600', false, true, 4.2, 89, 15, '["Beef Patty", "Lettuce", "Tomato", "Red Onion", "Pickles", "Special Sauce", "Sesame Bun"]', '["Gluten"]', '{"calories": 540, "protein": 25, "carbs": 35, "fat": 32, "fiber": 3, "sugar": 6, "sodium": 1050}', 'mild', '["classic", "popular"]'),

(12, 4, 8, 'BBQ Bacon Burger', 'Premium beef burger with crispy bacon, BBQ sauce, cheddar cheese, and caramelized onions', 189000, 219000, 14, 'https://images.unsplash.com/photo-1572802419224-296b0aeee0d9?w=600', false, true, 4.4, 145, 18, '["Beef Patty", "Crispy Bacon", "BBQ Sauce", "Cheddar Cheese", "Caramelized Onions", "Lettuce"]', '["Gluten", "Dairy"]', '{"calories": 620, "protein": 30, "carbs": 38, "fat": 38, "fiber": 3, "sugar": 12, "sodium": 1280}', 'mild', '["bbq", "bacon", "popular"]'),

(13, 4, 9, 'French Fries', 'Crispy golden french fries made from premium russet potatoes, served hot and fresh', 49000, null, 0, 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=600', true, true, 4.0, 78, 8, '["Russet Potatoes", "Vegetable Oil", "Sea Salt"]', '[]', '{"calories": 365, "protein": 4, "carbs": 63, "fat": 17, "fiber": 4, "sugar": 0, "sodium": 246}', 'none', '["crispy", "side-dish"]'),

(14, 4, 10, 'Cola', 'Classic cola soft drink served ice cold', 25000, null, 0, 'https://images.unsplash.com/photo-1527960471264-932f39eb5846?w=600', true, true, 4.1, 34, 1, '["Carbonated Water", "High Fructose Corn Syrup", "Caramel Color", "Natural Flavors", "Caffeine"]', '[]', '{"calories": 140, "protein": 0, "carbs": 39, "fat": 0, "fiber": 0, "sugar": 39, "sodium": 45}', 'none', '["refreshing", "carbonated"]');

-- ================================
-- 11. CARTS (Note: uses user_id, not User object)
-- ================================
INSERT INTO carts (id, user_id, created_at) VALUES
(1, 1, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(2, 2, DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(3, 5, DATE_SUB(NOW(), INTERVAL 30 MINUTE));

-- ================================
-- 12. CART ITEMS (Note: uses menu_item_id, not MenuItem object)
-- ================================
INSERT INTO cart_items (id, cart_id, menu_item_id, quantity, special_instructions, customizations, total_price) VALUES
(1, 1, 1, 2, 'Extra cheese, no olives', '[{"type":"size","value":"large","extraPrice":50000},{"type":"topping","value":"extra_cheese","extraPrice":25000}]', 1488000),
(2, 1, 4, 1, null, null, 45000),
(3, 2, 8, 1, 'Less spicy please', null, 89000),
(4, 2, 9, 1, null, null, 79000),
(5, 3, 11, 2, null, null, 318000),
(6, 3, 13, 1, null, null, 49000);

-- ================================
-- 13. ORDERS (Note: uses user_id, not User object, may have promo_code_id)
-- ================================
INSERT INTO orders (id, user_id, restaurant_id, total_price, delivery_charge, tax_amount, tax_percentage, final_total, promo_code_id, payment_method, active_status, is_self_pickup, order_note, delivery_tip, created_at) VALUES
(1, 1, 1, 1533000, 25000, 153300, 10, 1711300, null, 'CREDIT_CARD', 'DELIVERED', false, 'Please ring the doorbell twice', null, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(2, 2, 3, 168000, 20000, 16800, 10, 204800, null, 'CASH', 'DELIVERING', false, null, null, DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(3, 5, 4, 367000, 30000, 36700, 10, 433700, null, 'MOMO', 'PREPARING', false, 'Extra ketchup please', null, DATE_SUB(NOW(), INTERVAL 45 MINUTE)),
(4, 1, 2, 364000, 25000, 36400, 10, 425400, 1, 'VNPAY', 'COMPLETED', false, null, null, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(5, 2, 1, 228000, 25000, 22800, 10, 275800, null, 'CASH', 'CONFIRMED', false, 'Call when arrived', null, DATE_SUB(NOW(), INTERVAL 30 MINUTE)),
(6, 5, 3, 95000, 20000, 9500, 10, 124500, 2, 'ZALOPAY', 'PENDING', false, 'Extra herbs on the side', null, DATE_SUB(NOW(), INTERVAL 15 MINUTE));

-- ================================
-- 14. ORDER ITEMS (Note: uses menu_item_id, not MenuItem object)
-- ================================
INSERT INTO order_items (id, order_id, menu_item_id, quantity, price) VALUES
-- Order 1 (User 1, Restaurant 1) - Large mixed order
(1, 1, 1, 2, 669000),  -- Garden Fresh Pizza x2
(2, 1, 4, 1, 45000),   -- Orange Juice x1
(3, 1, 3, 1, 189000),  -- Grilled Chicken Sandwich x1

-- Order 2 (User 2, Restaurant 3) - Pho order
(4, 2, 8, 1, 89000),   -- Pho Bo Tai x1
(5, 2, 9, 1, 79000),   -- Pho Ga x1

-- Order 3 (User 5, Restaurant 4) - Burger order
(6, 3, 11, 2, 159000), -- Classic Beef Burger x2
(7, 3, 13, 1, 49000),  -- French Fries x1

-- Order 4 (User 1, Restaurant 2) - Pizza order (with promo code)
(8, 4, 5, 1, 329000),  -- Margherita Classic x1
(9, 4, 7, 1, 35000),   -- Italian Soda x1

-- Order 5 (User 2, Restaurant 1) - Single item order
(10, 5, 2, 1, 559000), -- Four Cheese Fantasy x1

-- Order 6 (User 5, Restaurant 3) - Spicy noodles (with promo code)
(11, 6, 10, 1, 95000); -- Bun Bo Hue x1

-- ================================
-- 15. FAVORITES (Note: uses user_id, not User object)
-- ================================
INSERT INTO favorites (id, user_id, favoritable_type, favoritable_id, created_at) VALUES
(1, 1, 'menu_item', 1, DATE_SUB(NOW(), INTERVAL 2 DAY)),   -- John likes Garden Fresh Pizza
(2, 1, 'menu_item', 8, DATE_SUB(NOW(), INTERVAL 1 DAY)),    -- John likes Pho Bo Tai
(3, 2, 'menu_item', 5, DATE_SUB(NOW(), INTERVAL 3 HOUR)),  -- Jane likes Margherita Classic
(4, 2, 'menu_item', 11, DATE_SUB(NOW(), INTERVAL 1 HOUR)),  -- Jane likes Classic Beef Burger
(5, 5, 'menu_item', 1, DATE_SUB(NOW(), INTERVAL 30 MINUTE)), -- Mike likes Garden Fresh Pizza
(6, 5, 'menu_item', 9, DATE_SUB(NOW(), INTERVAL 15 MINUTE)), -- Mike likes Pho Ga
(7, 1, 'menu_item', 12, DATE_SUB(NOW(), INTERVAL 1 HOUR)),  -- John likes BBQ Bacon Burger
(8, 2, 'menu_item', 10, DATE_SUB(NOW(), INTERVAL 2 HOUR)); -- Jane likes Bun Bo Hue

-- ================================
-- 16. SLIDER IMAGES (Note: simplified structure - no title/description)
-- ================================
INSERT INTO slider_images (id, image_url, link, sort_order) VALUES
(1, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=1200', '/restaurants/1', 1),
(2, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=1200', '/restaurants/3', 2),
(3, 'https://images.unsplash.com/photo-1551782450-a2132b4ba21d?w=1200', '/restaurants/4', 3),
(4, 'https://images.unsplash.com/photo-1553909489-cd47e0ef937f?w=1200', '/restaurants/2', 4);

-- ================================
-- 17. ADDRESSES (Note: Complete address structure)
-- ================================
INSERT INTO addresses (id, user_id, address_line, area, city_id, type, contact_name, mobile, country_code, alternate_mobile, landmark, pincode, state, country, latitude, longitude, is_default, created_at) VALUES
(1, 1, '123 Le Duan Street, District 1', 'District 1', 1, 'Home', 'John Doe', '0901234567', '+84', null, 'Near Ben Thanh Market', '70000', 'Ho Chi Minh', 'Vietnam', 10.7769, 106.7009, true, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(2, 1, '456 Nguyen Trai Street, District 5', 'District 5', 1, 'Work', 'John Doe', '0901234567', '+84', null, 'Saigon Square', '70000', 'Ho Chi Minh', 'Vietnam', 10.7559, 106.6869, false, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(3, 2, '789 Tran Hung Dao Street, District 1', 'District 1', 1, 'Home', 'Jane Smith', '0907654321', '+84', null, 'Notre-Dame Cathedral', '70000', 'Ho Chi Minh', 'Vietnam', 10.7797, 106.6950, true, DATE_SUB(NOW(), INTERVAL 4 DAY)),
(4, 2, '101 Hai Ba Trung Street, District 3', 'District 3', 1, 'Other', 'Jane Smith', '0907654321', '+84', null, 'War Remnants Museum', '70000', 'Ho Chi Minh', 'Vietnam', 10.7796, 106.6918, false, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(5, 5, '321 Bach Dang Street, Hai Chau', 'Hai Chau', 3, 'Home', 'Mike Wilson', '0934567890', '+84', null, 'Dragon Bridge', '50000', 'Da Nang', 'Vietnam', 16.0544, 108.2022, true, DATE_SUB(NOW(), INTERVAL 6 DAY)),
(6, 5, '654 Nguyen Van Linh Street, Thanh Khe', 'Thanh Khe', 3, 'Work', 'Mike Wilson', '0934567890', '+84', null, 'Con Market', '50000', 'Da Nang', 'Vietnam', 16.0678, 108.2208, false, DATE_SUB(NOW(), INTERVAL 1 DAY));

-- ================================
-- 18. RESET AUTO_INCREMENT VALUES
-- ================================
ALTER TABLE cities AUTO_INCREMENT = 6;
ALTER TABLE roles AUTO_INCREMENT = 4;
ALTER TABLE users AUTO_INCREMENT = 7;
ALTER TABLE user_profiles AUTO_INCREMENT = 7;
ALTER TABLE promo_codes AUTO_INCREMENT = 4;
ALTER TABLE restaurants AUTO_INCREMENT = 5;
ALTER TABLE menu_categories AUTO_INCREMENT = 11;
ALTER TABLE menu_items AUTO_INCREMENT = 15;
ALTER TABLE carts AUTO_INCREMENT = 4;
ALTER TABLE cart_items AUTO_INCREMENT = 7;
ALTER TABLE orders AUTO_INCREMENT = 7;
ALTER TABLE order_items AUTO_INCREMENT = 12;
ALTER TABLE favorites AUTO_INCREMENT = 9;
ALTER TABLE slider_images AUTO_INCREMENT = 5;
ALTER TABLE addresses AUTO_INCREMENT = 7;

-- ================================
-- 19. VERIFICATION QUERIES
-- ================================
SELECT '✅ CORRECTED Data insertion completed successfully!' as STATUS;
SELECT '📊 Database Statistics:' as INFO;
SELECT COUNT(*) as total_cities FROM cities;
SELECT COUNT(*) as total_users FROM users;
SELECT COUNT(*) as total_restaurants FROM restaurants;
SELECT COUNT(*) as total_menu_categories FROM menu_categories;
SELECT COUNT(*) as total_menu_items FROM menu_items;
SELECT COUNT(*) as total_orders FROM orders;
SELECT COUNT(*) as total_favorites FROM favorites;
SELECT COUNT(*) as total_cart_items FROM cart_items;
SELECT COUNT(*) as total_addresses FROM addresses;

-- ================================
-- 20. SAMPLE VALIDATION QUERIES
-- ================================
-- Check relationships
-- SELECT 'User-Profile Relationship Check' as test_name;
-- SELECT u.email, up.full_name FROM users u JOIN user_profiles up ON u.id = up.user_id;

-- SELECT 'Restaurant-MenuCategory Relationship Check' as test_name;
-- SELECT r.name as restaurant_name, mc.name as category_name 
-- FROM restaurants r 
-- JOIN menu_categories mc ON r.id = mc.restaurant_id;

-- SELECT 'Order-OrderItem Relationship Check' as test_name;
-- SELECT o.id as order_id, oi.menu_item_id, oi.quantity, oi.price
-- FROM orders o 
-- JOIN order_items oi ON o.id = oi.order_id
-- ORDER BY o.id;

-- SELECT 'Cart-CartItem Relationship Check' as test_name;
-- SELECT c.user_id, ci.menu_item_id, ci.quantity, ci.total_price
-- FROM carts c 
-- JOIN cart_items ci ON c.id = ci.cart_id; 