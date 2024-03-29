USE c_cs108_svellon;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<
  
DROP TABLE IF EXISTS Accounts;
DROP TABLE IF EXISTS UsersToAchievements;
DROP TABLE IF EXISTS Achievements;
DROP TABLE IF EXISTS FriendsTable;
DROP TABLE IF EXISTS FriendRequests;
DROP TABLE IF EXISTS Messages;
DROP TABLE IF EXISTS HistoryTable;
DROP TABLE IF EXISTS Questions;
DROP TABLE IF EXISTS Answers;
DROP TABLE IF EXISTS Quizzes;
DROP TABLE IF EXISTS QuizQuestionTable;
DROP TABLE IF EXISTS QuizTagTable;
DROP TABLE IF EXISTS HighScoreTable;
DROP TABLE IF EXISTS QuizReview;
DROP TABLE IF EXISTS Accouncements;

 -- remove table if it already exists and start from scratch

CREATE TABLE Accounts (
    username CHAR(64),
	displayname CHAR(64),
    passhash CHAR(80),
    salt CHAR(80),
	type CHAR(64),
	PRIMARY KEY(username)
);

CREATE TABLE UsersToAchievements (
	username CHAR(64),
	achievement_id VARCHAR(64),
	description VARCHAR(128),
	time_stamp TIMESTAMP,
	FOREIGN KEY (username) REFERENCES Accounts(username),
	FOREIGN KEY (achievement_id) REFERENCES Achievements(achievement_id)
);

CREATE TABLE Achievements (
	achievement_id VARCHAR(64),
	description VARCHAR(128),
	time_stamp TIMESTAMP,
	PRIMARY KEY(achievement_id)
);

CREATE TABLE FriendsTable (
	user CHAR(64),
	friends_name CHAR(64),
	FOREIGN KEY (user) REFERENCES Accounts(username),
	FOREIGN KEY (friends_name) REFERENCES ACCOUNTS(username)
);

CREATE TABLE FriendRequests (
	requester CHAR(64),
	requested CHAR(64),
	message VARCHAR(128),
	time_stamp TIMESTAMP,
	FOREIGN KEY (requester) REFERENCES Accounts(username),
	FOREIGN KEY (requested) REFERENCES Accounts(username)
);

CREATE TABLE Messages (
	message_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	sender CHAR(64),
	receiver CHAR(64),
	message VARCHAR(128),
	time_stamp TIMESTAMP,
	FOREIGN KEY (sender) REFERENCES Accounts(username),
	FOREIGN KEY (receiver) REFERENCES Accounts(username)
);

CREATE TABLE HistoryTable (
	history_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username CHAR(64),
	message VARCHAR(128),
	time_stamp TIMESTAMP,
	FOREIGN KEY (username) REFERENCES Accounts(username)
);

CREATE TABLE Questions (
	question_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	question VARCHAR(1024),
	description VARCHAR(128),
	type CHAR(64),
	creator CHAR(64),
	score INT,
	time_stamp TIMESTAMP,
	FOREIGN KEY (creator) REFERENCES Accounts(username)
);

CREATE TABLE Answers (
	question_id CHAR(64),
	answer VARCHAR(128),
	FOREIGN KEY (question_id) REFERENCES Questions(question_id)
);

CREATE TABLE Quizzes (
	quiz_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	quiz_name VARCHAR(128),
	creator CHAR(64),
	description VARCHAR(128),
	time_stamp TIMESTAMP,
	category CHAR(128),
	correct_immediately TINYINT(1),
	one_page TINYINT(1),
	random_order TINYINT(1),
	number_of_times_taken INT,
	number_of_reviews INT,
	average_rating FLOAT(5,3),
	FOREIGN KEY (creator) REFERENCES Accounts(username)
);

CREATE TABLE QuizQuestionTable (
	quiz_id CHAR(64),
	question_id CHAR(64),
	question_index INT,
	FOREIGN KEY (quiz_id) REFERENCES Quizzes(quiz_id),
	FOREIGN KEY (question_id) REFERENCES Questions(question_id)
);

CREATE TABLE QuizTagTable (
	quiz_id CHAR(64),
	tag VARCHAR(64),
	FOREIGN KEY (quiz_id) REFERENCES Quizzes(quiz_id)
);

CREATE TABLE HighScoreTable (
	quiz_id CHAR(64),
	history_id INT,
	FOREIGN KEY (quiz_id) REFERENCES Quizzes(quiz_id),
	FOREIGN KEY (history_id) REFERENCES HistoryTable(history_id)
);

CREATE TABLE QuizReview (
	quiz_id CHAR(64),
	username CHAR(64),
	comment VARCHAR(128),
	rating INT,
	FOREIGN KEY (quiz_id) REFERENCES Quizzes(quiz_id),
	FOREIGN KEY (username) REFERENCES Accounts(username)
);

CREATE TABLE Accouncements (
	username CHAR(64),
	announcement VARCHAR(128),
	FOREIGN KEY (username) REFERENCES Accounts(username)
);

INSERT INTO Accounts VALUES
	("john","John Smith","94f8f397b809abadcc4730044e28ba93a21d7db0","d47242a3114bc14d2309e9f45bec20f2f781b2d0","USER"),
	("sally","Sally Jones","1ba0f068fe9d0f424d92bf50a04dee5e9025617a","cba8a56de43d217c7f14fe64daf10f5186d2548d","USER");

INSERT INTO Messages (sender, receiver, message, time_stamp) VALUES
	("john","sally","Test message from John to Sally", NOW()),
	("john","sally","Another test message to Sally", NOW()),
	("sally","john","Test message from Sally to John", NOW()),
	("sally","john","Another test message to John",Now());

INSERT INTO Questions (question_id,question,description,type,creator,score,time_stamp) VALUES
	(1,"Who of the following was consul of Rome during the end of the Second Punic War?#Cato the Elder#Cato the Younger#Julius Caesar#Scipio Africanus?"
	,"This is a question about the Roman Republic.","MULTIPLE_CHOICE","sally",50,NOW()),
	(3,"Which Roman consul was defeated at the battle of Cannae?","This is a question about the Roman Republic.", "QUESTION_REPSONSE","sally","10",NOW());




INSERT INTO Quizzes (quiz_id,quiz_name, creator, description, time_stamp, category,
	correct_immediately, one_page, random_order, number_of_times_taken, number_of_reviews, average_rating) VALUES
	(1,"quiz on Ancient Rome","sally","This is a quiz on Ancient Rome",NOW(),"History",0,0,0,0,0,0.0);

INSERT INTO QuizQuestionTable VALUES
	(1, 1, 0),
	(1, 3, 1);
