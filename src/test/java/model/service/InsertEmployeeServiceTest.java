package model.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.dto.Department;
import model.dto.Employee;
import model.exception.ServiceException;
import util.TestUtil;

/**
 * UC03【社員登録】機能のテストクラス<br>
 *
 * @author Fukahori Yuga
 *
 */

/**
 *  * 後処理
 *  *
 *  * @throws Exception
 *  
 */

@DisplayName("UC03【社員登録】機能のテスト")
public class InsertEmployeeServiceTest {

    InsertEmployeeService target;

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        TestUtil.initDB();
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
    }

    @BeforeEach
    public void setUp() throws Exception {
        TestUtil.initDB();
        target = new InsertEmployeeService();
    }

    @Test
    @DisplayName("全ての部門情報を複数取得:データあり")
    public void testGetDeptInfo01() throws Exception {
        TestUtil.setDS101ToDB();
        List<Department> expected = TestUtil.getDS101();
        List<Department> actual = target.readDepartmentAll();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("全ての部門情報を複数取得:データなし")
    public void testGetDeptInfo02() throws Exception {
        TestUtil.setDS102ToDB();
        assertThrows(ServiceException.class, () -> target.readDepartmentAll());
    }

    @Test
    @DisplayName("全ての部門情報を複数取得:例外処理（取得失敗）")
    public void testGetDeptInfo03() throws Exception {
        TestUtil.clearDB();
        assertThrows(ServiceException.class, () -> target.readDepartmentAll());
    }

    @Test
    @DisplayName("全ての部門情報を複数取得:例外処理（DB処理エラー）")
    public void testGetDeptInfo04() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.readDepartmentAll());
        } finally {
            TestUtil.resetDBSetting();
        }
    }

    @Test
    @DisplayName("メールアドレスがリソースに登録済みのものと被っているかどうかを判定:被っていない")
    public void testIsDuplicateMailAddress01() throws Exception {
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();

        boolean expected = false;
        boolean actual = target.isDuplicateMailAddress(TestUtil.emp1004.getMailAddress());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("メールアドレスがリソースに登録済みのものと被っているかどうかを判定:被っている")
    public void testIsDuplicateMailAddress02() throws Exception {
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();

        boolean expected = true;
        boolean actual = target.isDuplicateMailAddress(TestUtil.emp1001.getMailAddress());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("メールアドレスがリソースに登録済みのものと被っているかどうかを判定:例外処理（取得失敗）")
    public void testIsDuplicateMailAddress03() throws Exception {
        TestUtil.clearDB();
        assertThrows(ServiceException.class,
                () -> target.isDuplicateMailAddress(TestUtil.emp1004.getMailAddress()));
    }

    @Test
    @DisplayName("メールアドレスがリソースに登録済みのものと被っているかどうかを判定:例外処理（DB処理エラー）")
    public void testIsDuplicateMailAddress04() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class,
                    () -> target.isDuplicateMailAddress(TestUtil.emp1004.getMailAddress()));
        } finally {
            TestUtil.resetDBSetting();
        }
    }

    @Test
    @DisplayName("渡された部門IDの部門情報を取得する:データあり")
    public void testReadDepartmentByDeptId01() throws Exception {
        TestUtil.setDS101ToDB();
        Department expected = TestUtil.dept101;
        Department actual = target.readDepartmentByDeptId(TestUtil.dept101.getDeptId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("渡された部門IDの部門情報を取得する:データなし")
    public void testReadDepartmentByDeptId02() throws Exception {
        TestUtil.setDS102ToDB();

        assertThrows(ServiceException.class, () -> target.readDepartmentByDeptId(TestUtil.dept104.getDeptId()));

    }

    @Test
    @DisplayName("渡された部門IDの部門情報を取得する:例外処理（取得失敗）")
    public void testIsDuplicateDeptName03() throws Exception {
        TestUtil.clearDB();
        assertThrows(ServiceException.class, () -> target.readDepartmentByDeptId(TestUtil.dept101.getDeptId()));

    }

    @Test
    @DisplayName("渡された部門IDの部門情報を取得する:例外処理（DB処理エラー）")
    public void testIsDuplicateDeptName04() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.readDepartmentByDeptId(TestUtil.dept101.getDeptId()));

        } finally {
            TestUtil.resetDBSetting();
        }
    }

    @Test
    @DisplayName("社員IDを新規に発行し引数の社員情報と合わせてリソースに登録:成功")
    public void testCreateEmployee01() throws Exception {
        TestUtil.setDS101ToDB();
        TestUtil.setDS002ToDB();

        assertDoesNotThrow(() -> target.createEmployee(TestUtil.emp1004));

        // 確実に登録されたかDBと接続して確認すべきところ、メソッドが登録された主キーを返さないため、エラーが出ないことのみ確認しています。
    }

    @Test
    @DisplayName("社員IDを新規に発行し引数の社員情報と合わせてリソースに登録:例外処理(更新失敗。ロールバックしました")
    public void testCreateEmployee02() throws Exception {
        TestUtil.clearDB();
        assertThrows(ServiceException.class, () -> target.createEmployee(TestUtil.emp1004));
    }

    @Test
    @DisplayName("社員IDを新規に発行し引数の社員情報と合わせてリソースに登録:例外処理（DB処理エラー）")
    public void testCreateEmployee03() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.createEmployee(TestUtil.emp1004));
        } finally {
            TestUtil.resetDBSetting();
        }
    }

}
