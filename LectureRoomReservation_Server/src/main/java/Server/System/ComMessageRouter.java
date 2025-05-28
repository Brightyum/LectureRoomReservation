/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.System;

import Model.BrokenComputer;
import java.util.List;

/**
 *
 * @author user
 */
public class ComMessageRouter {
    private final BrokenComputer brokenComputer;

    public ComMessageRouter(BrokenComputer brokenComputer) {
        this.brokenComputer = brokenComputer;
    }

    public String handleMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return "ERROR|Empty message";
        }

        String[] parts = message.split("\\|");
        String command = parts[0];

        if (command.equals("GET_BROKEN_LIST")) {
            List<String> brokenList = brokenComputer.getBrokenComputerList();
            if (brokenList.isEmpty()) {
                return "EMPTY|No broken computers";
            } else {
                return "SUCCESS|" + String.join(",", brokenList);
            }
        }

        return "ERROR|Unknown command";
    }
}
