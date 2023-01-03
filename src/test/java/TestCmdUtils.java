import com.automationanywhere.utils.CmdUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCmdUtils {

    @Test
    public void ShouldRunAnBasicCmdTask() {
        String command = "echo test";

        String result = CmdUtils.runOnCmd(command);

        Assert.assertEquals(result, "test");
    }

    @Test
    public void ShouldReturnOutputWhenErrorsOccur() {
        String command = "exho testing";

        String result = CmdUtils.runOnCmd(command);

        Assert.assertTrue(result.contains("Error:"));
    }
}
