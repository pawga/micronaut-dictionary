CREATE TABLE public."dictionary"
(
    id int PRIMARY KEY GENERATED BY DEFAULT AS identity,
    "name" varchar(255)  NOT NULL,
    CONSTRAINT unique_name UNIQUE ("name")
);

CREATE TABLE public.dictionary_value
(
    id int PRIMARY KEY GENERATED BY DEFAULT AS identity,
    code          varchar(255)  NOT NULL,
    value         varchar(255)  NOT NULL,
    dictionary_id int NULL references public."dictionary" (id)
);
