import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthenticatorTest {

	Authenticator auth = new Authenticator();

	@Test
	void loginUnitTest1() {
		int status = auth.login("user_a", "P@$$w0rd_a");
		assertEquals(1, status);
		//normal authentication
	}

	@Test
	void loginUnitTest2() {
		int status = auth.login("user_b", "P@$$w0rd_b");
		assertEquals(2, status);
		//inactive user
	}

	@Test
	void loginUnitTest3() {
		int status = auth.login("user_c", "P@$$w0rd_c");
		assertEquals(3, status);
	}

	@Test
	void loginUnitTest4() {
		int status = auth.login("user_a", "incorrect_password");
		assertEquals(4, status);
		//failed login attempt
	}
	
	
	@Test
	void loginUnitTest4_1() {
		int status = auth.login("' OR 'b'='b", "abc");
		assertEquals(4, status);
		//test for SQL injection
	}

	@Test
	void loginUnitTest4_2() {
		int status = auth.login("user_a", "' OR 'a'='a");
		assertEquals(4, status);
	}

	
	
	@Test
	void loginUnitTest5() {
		int status = auth.login("user_d", "P@$$w0rd_d");
		assertEquals(5, status);
	}

	@Test
	void loginUnitTest6() {
		int status = auth.login("user_e", "P@$$w0rd_e");
		assertEquals(6, status);
	}

	
	@Test
	void loginUnitTest7a() {
		int status = auth.login("user_a", "");
		assertEquals(7, status);
	}	

	
	@Test
	void loginUnitTest7b() {
		int status = auth.login("", "P@$$w0rd_a");
		assertEquals(7, status);
	}	
	
}
