CREATE TABLE IF NOT EXISTS `movies_issues` (
   `movie_id` VARCHAR(36) NOT NULL,
   `audio` VARCHAR(12),
   `subtitles` VARCHAR(12),
   `imdb_id` VARCHAR(12),
   PRIMARY KEY (`movie_id`),
   INDEX `fk_movies_issues_movies1_idx` (`movie_id` ASC),
   CONSTRAINT `fk_movies_issues_movies1`
       FOREIGN KEY (`movie_id`)
           REFERENCES `movies` (`id`)
           ON DELETE NO ACTION
           ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET = UTF8;
