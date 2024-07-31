-- Active: 1721153422344@@localhost@3306@hobbyTrackerDB
SELECT u.username, r.name 
FROM user u 
JOIN users_roles ur ON u.id = ur.user_id
JOIN role r ON r.id = ur.role_id
ORDER BY u.username;