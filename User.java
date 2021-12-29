package sa_project.app;

public abstract class User {
	protected int Id;
	protected String Name;
	protected String Email;
	protected String Password;
	protected String Department;
	
	public abstract int getId();
	public abstract String getName();
	public abstract String getEmail();
	public abstract String getPassword();
	public abstract String getDepartment();
	public abstract int getCourseListId();
}