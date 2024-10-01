-- Create User Table
CREATE TABLE adm_users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name_id VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    image_path VARCHAR(255),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE post_details (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT NOT NULL,
    updated_by INT NOT NULL,
    status VARCHAR(50) NOT NULL, -- You can insert any status value programmatically
    category VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user_table(user_id) -- Assuming user_table is the name of the users table
);

CREATE TABLE post_operation_data (
    post_operation_id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    post_type VARCHAR(50) NOT NULL, -- Flexible post type
    comment TEXT, -- Optional comment
    is_deleted BOOLEAN DEFAULT FALSE, -- 0 for not deleted, 1 for deleted
    operation_by INT NOT NULL, -- Refers to the user who performed the operation
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Automatically sets the creation timestamp
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Updates the timestamp on modification
    updated_by INT, -- User ID who last updated the post operation
    FOREIGN KEY (post_id) REFERENCES post_details(post_id) -- Assuming post_details is the post table
);

CREATE TABLE hash_tag_posts (
    hash_tag_id INT AUTO_INCREMENT PRIMARY KEY,
    hash_tag VARCHAR(100) NOT NULL, -- To store the hashtag
    post_id INT NOT NULL, -- Refers to the related post
    FOREIGN KEY (post_id) REFERENCES post_details(post_id) -- Links to the post_details table
);

