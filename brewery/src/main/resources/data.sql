-- Create the favorite table
CREATE TABLE IF NOT EXISTS favorite (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_id VARCHAR(255) NOT NULL,
    brewery_id VARCHAR(255) NOT NULL
    );

-- Insert initial data into favorite table
INSERT INTO favorite (id, user_id, brewery_id) VALUES (1, 'user1', '5128df48-79fc-4f0f-8b52-d06be54d0cec');
