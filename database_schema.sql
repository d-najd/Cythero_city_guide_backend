Table users {
  id varchar [pk]
  username varchar [not null, unique]
}

Table locations {
  id bigint [pk]
  longitude decimal(20, 12) [not null] // compatible with java.math.BigDecimal
  latitude decimal(20,12) [not null] // compatible with java.math.BigDecimal
  address varchar [not null, unique]
  flag_path varchar // [unique]
  indexes {
    (longitude, latitude) [unique]
  }
}

Table countries {
  id bigint [pk]
  location_id bigint [not null, unique]
  name varchar [not null, unique]
}

Ref: countries.location_id > locations.id [delete: cascade]

Table cities {
  id bigint [pk]
  country_id bigint [not null]
  location_id bigint [unique, not null]
  name varchar [not null] // intentionally not unique
}

Ref: cities.country_id > countries.id [delete: cascade]
Ref: cities.location_id > locations.id [delete: cascade]

Table tourist_attractions {
  id bigint [pk]
  city_id bigint [not null]
  location_id bigint [not null]
  name varchar [not null]
  description text // text since description may be bigger than 255 characters
}

Ref: tourist_attractions.city_id > cities.id [delete: cascade]
Ref: tourist_attractions.location_id > locations.id [delete: cascade]

Table reviews {
  id bigint [pk]
  attraction_id bigint [not null]
  user_id varchar [not null]
  stars int1 [not null] // 0-9 for 1-10 rating
  title varchar [not null]
  description text // text since description may be bigger than 255 characters
  indexes {
    (attraction_id, user_id) [unique]
  }
}

Ref: reviews.attraction_id > tourist_attractions.id [delete: cascade]
Ref: reviews.user_id > users.id [delete: cascade]

Table images {
  id bigint [pk]
  city_id bigint
  attraction_id bigint
  review_id bigint
  path varchar [not null]
}

Ref: images.city_id > cities.id [delete: cascade]
Ref: images.attraction_id > tourist_attractions.id [delete: cascade]
Ref: images.review_id > reviews.id [delete: cascade]

Table attraction_types {
  id bigint [pk]
  type varchar [not null, unique]
}

Table attraction_type {
  id bigint [pk]
  attraction_id bigint
  type_id bigint
  indexes {
    (attraction_id, type_id) [unique]
  }
}

Ref: attraction_type.type_id > attraction_types.id
Ref: attraction_type.attraction_id > tourist_attractions.id