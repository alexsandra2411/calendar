CREATE TABLE IF NOT EXISTS calendar_entry (
  id integer not null,
  description VARCHAR(128),
  date DATETIME,
  primary key(id)
);