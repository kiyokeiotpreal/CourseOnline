CREATE TABLE `roles` (
  `Id` integer PRIMARY KEY,
  `AuthorityName` varchar(255)
);

CREATE TABLE `users` (
  `Id` integer PRIMARY KEY,
  `avatar` varchar(255),
  `username` varchar(255),
  `password` varchar(255),
  `email` varchar(255),
  `dateOfBirth` timestamp,
  `gender` bit,
  `address` varchar(255),
  `firstName` varchar(255),
  `lastName` varchar(255),
  `roleId` varchar(255),
  `isActive` bit
);

CREATE TABLE `refreshTokens` (
  `Id` integer PRIMARY KEY,
  `token` varchar(255),
  `expiredDate` timestamp,
  `modifiefDate` timestamp,
  `userId` integer
);

CREATE TABLE `notification` (
  `Id` integer PRIMARY KEY,
  `userId` integer,
  `isSeen` bit,
  `content` varchar(255),
  `create` timestamp
);

CREATE TABLE `majors` (
  `Id` integer PRIMARY KEY,
  `Name` varchar(255)
);

CREATE TABLE `learningExperience` (
  `Id` integer PRIMARY KEY,
  `majorsId` integer,
  `userId` integer,
  `fromDate` timestamp,
  `toDate` timestamp
);

CREATE TABLE `Post` (
  `Id` integer PRIMARY KEY,
  `Description` varchar(255),
  `ImagePost` varchar(255),
  `UserCreatePostId` integer,
  `CreateAt` timestamp,
  `UpdateAt` timestamp,
  `likeCount` integer,
  `commentCount` integer
);

CREATE TABLE `ApprovePost` (
  `Id` integer PRIMARY KEY,
  `adminApprovePostId` integer,
  `postId` integer,
  `statusPost` varchar(255),
  `CreateAt` timestamp,
  `UpdateAt` timestamp
);

CREATE TABLE `PostSentence` (
  `Id` integer PRIMARY KEY,
  `postId` integer,
  `ImagePost` varchar(255),
  `ImageTitle` varchar(255),
  `Content` varchar(255),
  `SortNumber` integer
);

CREATE TABLE `AdviceContact` (
  `Id` integer PRIMARY KEY,
  `Name` varchar(255),
  `PhoneNumber` varchar(255),
  `IsContact` bit,
  `TimeSendRequest` timestamp,
  `UserContactId` integer,
  `ContactTimeBegin` integer,
  `ContactTimeEnd` integer,
  `Rate` integer,
  `Evaluate` varchar(255),
  `Note` varchar(255),
  `Status` integer
);

CREATE TABLE `CommentPost` (
  `Id` integer PRIMARY KEY,
  `postId` integer,
  `UserCommentId` integer,
  `Comment` varchar(255),
  `likeCount` integer,
  `dislikeCount` integer,
  `CreateAt` timestamp,
  `UpdateAt` timestamp,
  `RemoveAt` timestamp,
  `isActive` bit
);

CREATE TABLE `CommentLesson` (
  `Id` integer PRIMARY KEY,
  `lessonId` integer,
  `Comment` varchar(255),
  `UserCommentId` integer,
  `likeCount` integer,
  `dislikeCount` integer,
  `CreateAt` timestamp,
  `UpdateAt` timestamp,
  `RemoveAt` timestamp,
  `isActive` bit
);

CREATE TABLE `Lesson` (
  `Id` integer PRIMARY KEY,
  `Name` varchar(255),
  `Description` varchar(255),
  `CreateAt` timestamp,
  `UpdateAt` timestamp,
  `RemoveAt` timestamp,
  `LessonTypeId` integer
);

CREATE TABLE `LessonType` (
  `Id` integer PRIMARY KEY,
  `Name` varchar(255)
);

CREATE TABLE `UserCourse` (
  `Id` integer PRIMARY KEY,
  `userId` integer,
  `courseId` integer,
  `isDone` bit,
  `RegisterTime` timestamp,
  `LastTimeStudy` timestamp,
  `DoneTime` timestamp
);

CREATE TABLE `UserLessonCheckpoint` (
  `Id` integer PRIMARY KEY,
  `lessonId` integer,
  `userId` integer,
  `OpenLessonTime` timestamp,
  `isDone` bit
);

CREATE TABLE `UserLikeCommentLesson` (
  `Id` integer PRIMARY KEY,
  `userLikeCommentId` integer,
  `commentLessonId` integer,
  `CreateAt` timestamp,
  `isDeleted` bit
);

CREATE TABLE `UserLikeCommentPost` (
  `Id` integer PRIMARY KEY,
  `userLikeCommentId` integer,
  `CommentPostId` integer,
  `CreateAt` timestamp,
  `isDeleted` bit
);

CREATE TABLE `UserLikePost` (
  `Id` integer PRIMARY KEY,
  `userId` integer,
  `postId` integer,
  `likeTime` timestamp,
  `isDeleted` bit
);

CREATE TABLE `Course` (
  `Id` integer PRIMARY KEY,
  `NameOfCourse` varchar(255),
  `CourseSubTitle` varchar(255),
  `ImageCourse` varchar(255),
  `CourseLevelId` integer,
  `LessonCount` integer,
  `ChapterCount` integer,
  `TimeLessonTotal` varchar(255),
  `RegisterCount` integer,
  `DoneCount` integer,
  `CourseTypeId` integer
);

CREATE TABLE `CourseLevel` (
  `Id` integer PRIMARY KEY,
  `Name` varchar(255)
);

CREATE TABLE `CourseType` (
  `Id` integer PRIMARY KEY,
  `Name` varchar(255)
);

ALTER TABLE `users` ADD FOREIGN KEY (`roleId`) REFERENCES `roles` (`Id`);

ALTER TABLE `refreshTokens` ADD FOREIGN KEY (`userId`) REFERENCES `users` (`Id`);

ALTER TABLE `notification` ADD FOREIGN KEY (`userId`) REFERENCES `users` (`Id`);

ALTER TABLE `learningExperience` ADD FOREIGN KEY (`userId`) REFERENCES `users` (`Id`);

ALTER TABLE `learningExperience` ADD FOREIGN KEY (`majorsId`) REFERENCES `majors` (`Id`);

ALTER TABLE `Post` ADD FOREIGN KEY (`UserCreatePostId`) REFERENCES `users` (`Id`);

ALTER TABLE `PostSentence` ADD FOREIGN KEY (`postId`) REFERENCES `Post` (`Id`);

ALTER TABLE `AdviceContact` ADD FOREIGN KEY (`UserContactId`) REFERENCES `users` (`Id`);

ALTER TABLE `ApprovePost` ADD FOREIGN KEY (`adminApprovePostId`) REFERENCES `users` (`Id`);

ALTER TABLE `ApprovePost` ADD FOREIGN KEY (`postId`) REFERENCES `Post` (`Id`);

ALTER TABLE `CommentPost` ADD FOREIGN KEY (`postId`) REFERENCES `Post` (`Id`);

ALTER TABLE `CommentPost` ADD FOREIGN KEY (`UserCommentId`) REFERENCES `users` (`Id`);

ALTER TABLE `CommentLesson` ADD FOREIGN KEY (`lessonId`) REFERENCES `Lesson` (`Id`);

ALTER TABLE `CommentLesson` ADD FOREIGN KEY (`UserCommentId`) REFERENCES `users` (`Id`);

ALTER TABLE `Lesson` ADD FOREIGN KEY (`LessonTypeId`) REFERENCES `LessonType` (`Id`);

ALTER TABLE `UserCourse` ADD FOREIGN KEY (`courseId`) REFERENCES `Course` (`Id`);

ALTER TABLE `UserCourse` ADD FOREIGN KEY (`userId`) REFERENCES `users` (`Id`);

ALTER TABLE `UserLessonCheckpoint` ADD FOREIGN KEY (`userId`) REFERENCES `users` (`Id`);

ALTER TABLE `UserLessonCheckpoint` ADD FOREIGN KEY (`lessonId`) REFERENCES `Lesson` (`Id`);

ALTER TABLE `UserLikeCommentLesson` ADD FOREIGN KEY (`userLikeCommentId`) REFERENCES `users` (`Id`);

ALTER TABLE `UserLikeCommentLesson` ADD FOREIGN KEY (`commentLessonId`) REFERENCES `CommentLesson` (`Id`);

ALTER TABLE `UserLikeCommentPost` ADD FOREIGN KEY (`CommentPostId`) REFERENCES `CommentPost` (`Id`);

ALTER TABLE `UserLikeCommentPost` ADD FOREIGN KEY (`userLikeCommentId`) REFERENCES `users` (`Id`);

ALTER TABLE `UserLikePost` ADD FOREIGN KEY (`userId`) REFERENCES `users` (`Id`);

ALTER TABLE `UserLikePost` ADD FOREIGN KEY (`postId`) REFERENCES `Post` (`Id`);

ALTER TABLE `Course` ADD FOREIGN KEY (`CourseLevelId`) REFERENCES `CourseLevel` (`Id`);

ALTER TABLE `Course` ADD FOREIGN KEY (`CourseTypeId`) REFERENCES `CourseType` (`Id`);
