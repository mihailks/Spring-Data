
INSERT INTO shampoo_company.labels(id, title, subtitle)
VALUES (1, 'Repair & Nutrition', 'For naturally beautiful hair from roots to tips!'),
       (2, 'Taming & Anti-Split Ends', 'For unruly hair with split ends.'),
       (3, 'Volume & Fullness', 'Flat & thin hair.'),
       (4, 'Strength & Nourishing', 'Brittle & fragile hair.'),
       (5, 'Shine & Hydration', 'Dry & dull hair.'),
       (6, 'Color Protection & Radiance', 'Colored or highlighted hair'),
       (7, 'Oil Repair', 'Dry & depleted hair'),
       (8, 'Long-Hair', 'Dull & shine less and Difficult to comb'),
       (9, 'Vital', 'Tired & lifeless hair'),
       (10, 'Power Volume', 'Fine & flat hair'),
       (11, 'Anti-Dandruff', 'Quickly greasing & flaky hair');
USE shampoo_company;
INSERT INTO shampoo_company.ingredients(id, name, price)
VALUES (1, 'Apple', '0.50'),
       (2, 'Nettle', '0.70'),
       (3, 'Macadamia Oil', '1.00'),
       (4, 'Aloe Vera', '0.90'),
       (5, 'Lavender', '0.70'),
       (6, 'Herbs', '0.70'),
       (7, 'Wild Rose', '0.80'),
       (8, 'Raspberry', '0.70'),
       (9, 'Cherry', 0.70),
       (10, 'Berry', '0.50'),
       (11, 'Mineral-Collagen', '1'),
       (12, 'Zinc Pyrithione', '1.20'),
       (13, 'Micro-Crystals', '0.90'),
       (14, 'Active-Caffeine', '1.20');

INSERT INTO shampoo_company.shampoos(id, brand, price, size, label)
VALUES (1, 'Swiss Green Apple & Nettle', '3.50', '0', 4),
       (2, 'Moroccan Argan Oil & Macadamia', '5.50', '0', 9),
       (3, 'Nature Moments Mediterranean Olive Oil & Aloe Vera', '6.50', '1', 3),
       (4, 'Volume & Fullness Lavender', '5.50', '1', 9),
       (5, 'Strength & Nourishing Elixir', '4.50', '0', 3),
       (6, 'Rose Shine & Hydration', '6.50', '1', 5),
       (7, 'Color Protection & Radiance', '6.75', '1', 5),
       (8, 'Intense-Charm Brunette', '5.50', '0', 10),
       (9, 'Heavenly Long Long-Hair', '7.50', '1', 8),
       (10, 'Sea Buckthorn Vital', '6.50', '1', 4),
       (11, 'Fresh it Up!', '7.65', '1', 11),
       (12, 'Nectar Nutrition', '6.85', '1', 5),
       (13, 'Keratin Strong', '8.80', '0', 9),
       (14, 'Superfruit Nutrition', '8.80', '0', 5),
       (15, 'Cotton Fresh', '8.80', '0', 2),
       (16, 'Silk Comb', '7.80', '0', 9),
       (17, 'Active-Caffeine', '7.80', '0', 10),
       (18, 'Volume & Fullness Lavender', '8.50', '2', 9),
       (19, 'Strength & Nourishing Elixir', '7.20', '2', 3),
       (20, 'Rose Shine & Hydration', '10.70', '2', 5),
       (21, 'Color Protection & Radiance', '11.50', '2', 5),
       (22, 'Nectar Nutrition', '10.50', '2', 5),
       (23, 'Keratin Strong', '8.80', '2', 9),
       (24, 'Superfruit Nutrition', '13.80', '2', 5),
       (25, 'Cotton Fresh', '13.60', '2', 2);

INSERT INTO shampoos_ingredients(shampoo_id, ingredient_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 11),
       (3, 5),
       (4, 11),
       (3, 6),
       (5, 4),
       (5, 7),
       (6, 4),
       (6, 7),
       (7, 8),
       (7, 9),
       (7, 10),
       (8, 3),
       (8, 7),
       (9, 12),
       (9, 8),
       (10, 6),
       (11, 13),
       (11, 10),
       (12, 4),
       (12, 10),
       (12, 9),
       (13, 11),
       (13, 3),
       (14, 1),
       (14, 10),
       (14, 9),
       (15, 4),
       (15, 5),
       (16, 13),
       (16, 4),
       (17, 13),
       (18, 11),
       (18, 6),
       (19, 4),
       (19, 7),
       (20, 4),
       (20, 7),
       (21, 8),
       (21, 9),
       (21, 10),
       (22, 4),
       (22, 10),
       (22, 9),
       (23, 11),
       (23, 3),
       (24, 1),
       (24, 10),
       (24, 9),
       (25, 4),
       (25, 5);