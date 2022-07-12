db = db.getSiblingDB('db_logs');

db.createUser({
    user: 'kientd',
    pwd: '123456',
    roles: [
        {
            role: 'readWrite',
            db: 'db_logs',
        },
    ],
});