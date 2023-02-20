USE [campDB]
GO
/****** Object: Table [dbo].[userprofiles] Script Date: 2023/2/15 下午 01:02:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userprofiles](
[uid] binary NOT NULL,
[accountnonexpired] [boolean] NULL,
[accountnonlocked] [boolean] NULL,
[email] varchar NOT NULL,
[iscredentialsnonexpired] [boolean] NULL,
[isenabled] [boolean] NULL,
[password] varchar NULL,
[about] varchar NULL,
[exp] [bigint] NULL,
[gender] [int] NULL,
[level] [int] NULL,
[nickname] varchar NULL,
[point] [bigint] NULL,
[registerdata] [timestamp] NULL,
[shot] varchar NULL,
[subscribed] [boolean] NULL,
[firstname] varchar NULL,
[lastname] varchar NULL,
[address] varchar NULL,
[birthday] [timestamp] NULL,
[phone] varchar NULL,
PRIMARY KEY (
[uid]
)
)
GO
INSERT INTO [dbo].[userprofiles] ([uid], [accountnonexpired], [accountnonlocked], [email], [iscredentialsnonexpired], [isenabled], [password], [about], [exp], [gender], [level], [nickname], [point], [registerdata], [shot], [subscribed], [firstname], [lastname], [address], [birthday], [phone]) VALUES (X'41600F10963D4F53A50E87E2907DF1BC', true, true, 'jackEEIT56@gmail.com', true, true, '$2a$10$lWZOgn64ymCyhoTWHFG4o.TSS0SP0.iMi4UyB8rw.jMppULjgpaGC', '暫時沒有留下什麼', 1, 0, 1, 'jackEEIT56@gmail.com', 100, '2023-02-14 20:13:49.59', 'https://storage.googleapis.com/morariphoto/defaultshot', false, '', '', '', null, '')
