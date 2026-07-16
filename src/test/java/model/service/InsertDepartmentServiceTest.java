package model.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.exception.ServiceException;
import util.TestUtil;

@DisplayName("UC04【部門登録】機能のテスト")
public class InsertDepartmentServiceTest {

    /**
     * UC04【部門登録】機能のテストクラス<br>
     *
     * @author Fullness, Inc.
     *
     */

    /**
     * テスト対象
     */
    InsertDepartmentService target;

    /**
     * 後処理
     * 
     * @throws Exception
     */
    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        TestUtil.initDB();
        TestUtil.setDS101ToDB();
    }

    /**
     * 各テスト前に実行
     * 
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        TestUtil.initDB();
        target = new InsertDepartmentService();
    }

    @Test
    @DisplayName("部門名が登録済のものと被っているかどうかを判定:被っていない")
    public void testIsDuplicateDeptName01() throws Exception {
        TestUtil.setDS101ToDB();
        boolean expected = false;
        boolean actual = target.isDuplicateDeptName(TestUtil.dept104.getDeptName());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("部門名が登録済のものと被っているかどうかを判定:被っている")
    public void testIsDuplicateDeptName02() throws Exception {
        TestUtil.setDS101ToDB();
        boolean expected = true;
        boolean actual = target.isDuplicateDeptName(TestUtil.dept103.getDeptName());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("部門名が登録済のものと被っているかどうかを判定:例外処理（取得失敗）")
    public void testIsDuplicateDeptName03() throws Exception {
        TestUtil.clearDB();
        assertThrows(ServiceException.class, () -> target.isDuplicateDeptName(TestUtil.dept104.getDeptName()));
    }

    @Test
    @DisplayName("社員情報とその社員が所属する部門名を複数取得:例外処理（DB処理エラー）")
    public void testIsDuplicateDeptName04() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.isDuplicateDeptName(TestUtil.dept104.getDeptName()));
        } finally {
            TestUtil.resetDBSetting();
        }
    }

    @Test
    @DisplayName("部門IDを新規に発行し引数の部門情報と合わせてリソースに登録:成功")
    public void testCreateDepartment01() throws Exception {
        TestUtil.setDS101ToDB();

        assertDoesNotThrow(() -> target.createDepartment(TestUtil.dept104));

        // 確実に登録されたかDBと接続して確認すべきところ、メソッドが登録された主キーを返さないため、エラーが出ないことのみ確認しています。
    }

    @Test
    @DisplayName("部門IDを新規に発行し引数の部門情報と合わせてリソースに登録:例外処理(更新失敗。ロールバックしました")
    public void testCreateDepartment02() throws Exception {
        TestUtil.clearDB();
        assertThrows(ServiceException.class, () -> target.createDepartment(TestUtil.dept103));
    }

    @Test
    @DisplayName("部門IDを新規に発行し引数の部門情報と合わせてリソースに登録:例外処理（DB処理エラー）")
    public void testCreateDepartment03() throws Exception {
        TestUtil.changeDBSetting();
        try {
            assertThrows(ServiceException.class, () -> target.createDepartment(TestUtil.dept104));
        } finally {
            TestUtil.resetDBSetting();
        }
    }

}
