package model.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import model.dto.Employee;
import model.exception.ServiceException;
import util.TestUtil;

@DisplayName("UC05【社員削除】機能のテスト")
public class DeleteEmployeeServiceTest {

    DeleteEmployeeService target;

    int trueEmpId = 1001;
    int falseEmpId = 2000;

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        TestUtil.initDB();
        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
    }

    @BeforeEach
    public void setUp() throws Exception {
        TestUtil.initDB();
        target = new DeleteEmployeeService();
    }

    @Test
    @DisplayName("引数が正しく、DB接続ができていない:例外処理（DB処理エラー）")
    public void testDeleteEmpService_getInfo01() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.readEmployeeByEmpId(trueEmpId));
        } finally {
            TestUtil.resetDBSetting();
        }
    }

    @Test
    @DisplayName("引数が存在し、DB接続ができるが、DBにデータがない:例外処理（取得失敗）")
    public void testDeleteEmpService_getInfo02() throws Exception {
        TestUtil.clearDB();
        assertThrows(ServiceException.class, () -> target.readEmployeeByEmpId(trueEmpId));
    }

    @Test
    @DisplayName("引数が存在せず、DB接続ができ、DBにデータがある:例外処理（取得件数が0件）")
    public void testDeleteEmpService_getInfo03() throws Exception {

        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
        assertThrows(ServiceException.class, () -> target.readEmployeeByEmpId(falseEmpId));

    }

    @Test
    @DisplayName("引数が存在し、DB接続ができ、DBにデータがある")
    public void testDeleteEmpService_getInfo04() throws Exception {

        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
        Employee expected = TestUtil.emp1001;
        Employee actual = target.readEmployeeByEmpId(trueEmpId);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("引数が正しく、DB接続ができていない:例外処理（DB処理エラー）")
    public void testDeleteEmpService_deleteInfo05() throws Exception {

        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.deleteEmployee(TestUtil.emp1001));
        } finally {
            TestUtil.resetDBSetting();
        }
    }

    @Test
    @DisplayName("引数が存在し、DB接続ができるが、DBにデータがない:例外処理（取得失敗）")
    public void testDeleteEmpService_deleteInfo06() throws Exception {

        TestUtil.clearDB();
        assertThrows(ServiceException.class, () -> target.deleteEmployee(TestUtil.emp1001));
    }

    @Test
    @DisplayName("引数が存在せず、DB接続ができ、DBにデータがある:例外処理（取得件数が0件）")
    public void testDeleteEmpService_deleteInfo07() throws Exception {

        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
        assertThrows(ServiceException.class, () -> target.deleteEmployee(TestUtil.emp1004));
    }

    @Test
    @DisplayName("引数が存在し、DB接続ができ、DBにデータがある")
    public void testDeleteEmpService_deleteInfo08() throws Exception {

        TestUtil.setDS101ToDB();
        TestUtil.setDS001ToDB();
        assertDoesNotThrow(() -> target.deleteEmployee(TestUtil.emp1001));

    }

}
