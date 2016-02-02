/*
 * command line: thrift-xxx.exe -r -gen java -out ../src/main/java/com/xxx... UserService.thrift
 */
struct User {
	1:i32 id,
	2:string mame,
	3:i32 age,
	4:i64 timestamp
}

service UserService {
	User getById(1:i32 id)

	string update(1:User user)
}