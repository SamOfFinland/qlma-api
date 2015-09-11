-- name: select-all-messages
-- Select all messages from db
SELECT * FROM messages

-- name: select-message-with-id
-- Select message with id
SELECT * FROM messages WHERE id = :id

-- name: select-messages-to-user
-- Select messages to user
SELECT * FROM messages WHERE to_user_id = :user_id

-- name: insert-new-message<!
-- Create new message to user
INSERT INTO messages (from_user_id, to_user_id, message) values(:from, :to, :message)
