INSERT IGNORE INTO users VALUES ("1", "user1");

INSERT IGNORE INTO users VALUES ("2", "user2");

INSERT IGNORE INTO users VALUES ("3", "user3");

INSERT IGNORE INTO locations VALUES (1, 100.02, 50.6342871, "Macedonia - Bitola?", "");

INSERT IGNORE INTO locations VALUES (2, 632.2, 1245.12, "Macedonia - ?", "");

INSERT IGNORE INTO countries VALUES (1, 1, "Macedonia");

INSERT IGNORE INTO countries VALUES (2, 2, "Macedonia2");

INSERT IGNORE INTO cities VALUES (1, 1, 1, "Bitola");

INSERT IGNORE INTO cities VALUES (2, 1, 2, "Skopje");

INSERT IGNORE INTO tourist_attractions VALUES (1, 2, 1, "Portal", null);

INSERT IGNORE INTO tourist_attractions VALUES (2, 2, 1, "Portal2", null);

INSERT IGNORE INTO reviews VALUES (1, 1, "1", 9, "Good", null);

INSERT IGNORE INTO reviews VALUES (2, 1, "2", 0, "Bad", "Description");

INSERT IGNORE INTO images VALUES (1, 1, null, null, "http://somepath.com/img.jpg");

INSERT IGNORE INTO images VALUES (2, null, 1, null, "http://somepath.com/img.jpg");

INSERT IGNORE INTO images VALUES (3, null, null, 1, "http://somepath.com/img.jpg");