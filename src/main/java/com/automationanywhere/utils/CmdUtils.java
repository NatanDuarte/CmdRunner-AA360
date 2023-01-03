package com.automationanywhere.utils;

import io.github.alekseysotnikov.cmd.core.Cmd;
import io.github.alekseysotnikov.cmd.listeners.WorkDir;
import org.jetbrains.annotations.NotNull;
import org.zeroturnaround.exec.StartedProcess;

public class CmdUtils {

    public static @NotNull String runOnCmd(String commands) {
        commands = commands.replaceAll("[\\n\\r+]", " ")
                .replaceAll("\\^+", "")
                .replaceAll("\\s+", " ")
                .trim();

        String base = "cmd.exe /c ";
        commands = base + commands;

        try {
            StartedProcess startedProcess = new Cmd()
                    .configuring(new WorkDir("C:\\"), c -> c.readOutput(true))
                    .command(commands.split("\\s+"))
                    .start();

            String result = startedProcess.getFuture()
                    .get()
                    .outputUTF8()
                    .replaceAll("[\\n|\\r+]$", "")
                    .replaceAll("\\r$", "");

            if (startedProcess.getFuture().get().getExitValue() > 0) return "Error: " + result;

            return result;
        } catch (Exception e) {
            return "Error: " + e;
        }
    }
}
