package com.automationanywhere.botcommand;

import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.utils.CmdUtils;

import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

//BotCommand makes a class eligible for being considered as an action.
@BotCommand

//CommandPks adds required information to be dispalable on GUI.
@CommandPkg(
        //Unique name inside a package and label to display.
        name = "RunOnCmd",
        label = "[[RunCmdTask.details.label]]",
        node_label = "[[RunCmdTask.details.node_label]]",
        description = "[[RunCmdTask.details.description]]", icon = "pkg.svg",

        //Return type information. return_type ensures only the right kind of variable is provided on the UI.
        return_label = "[[RunCmdTask.details.return_label]]",
        return_type = STRING,
        return_required = true,
        return_description = "[[RunCmdTask.details.return_label_description]]"
)
public class RunOnCmd {

    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public StringValue action(
            //Idx 1 would be displayed first, with a text box for entering the value.
            @Idx(index = "1", type = TEXT)
            //UI labels.
            @Pkg(label = "[[RunCmdTask.details.string_message.label]]")
            //Ensure that a validation error is thrown when the value is null.
            @NotEmpty
            String command
    ) {
        //Internal validation, to disallow empty strings. No null check needed as we have NotEmpty on firstString.
        if ("".equals(command.trim()))
            throw new BotCommandException("[[RunCmdTask.details.error_string_empty]]");

        //Business logic
        String stringJson = doBusinessLogic(command);

        //Return NumberValue
        return new StringValue(stringJson);
    }

    private static String doBusinessLogic(String command) {
        String output;
        try {
            output = CmdUtils.runOnCmd(command);
        } catch (Exception e) {
            throw new BotCommandException("[[RunCmdTask.details.error_on_decoding]]" + e);
        }
        return output;
    }
}
