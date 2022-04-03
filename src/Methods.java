import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Methods {
    public static String passwordValidationMessage(String password) {
        StringBuilder message = new StringBuilder();

        HashMap<String, String> validation = new HashMap<>();
        validation.put("^(?=.*[a-z]).{1,}$", "add lowercase letter");
        validation.put("^(?=.*[A-Z]).{1,}$", "add uppercase letter");
        validation.put("^(?=.*[0-9]).{1,}$", "add number");
        validation.put("^(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{1,}$", "add special character");
        validation.put("^(?=\\S+$).{1,}$", "remove spaces");
        validation.put("^.{8,20}$", "limit 8-20 characters");

        for (String i : validation.keySet())
            if (!Pattern.compile(i).matcher(password).matches())
                message.append("&nbsp;•&nbsp;&nbsp;").append(validation.get(i)).append("<br>");

        if (message.isEmpty()) {
            return "";
        }

        message.insert(0, "<html><body><div style = \"width: 230px;\">");
        message.append("</div></body></html>");

        return message.toString();
    }

    public static boolean isNotNumber(String stringNumber) {
        if (stringNumber == null)
            return true;

        try {
            Integer.parseInt(stringNumber);
            Float.parseFloat(stringNumber);
        } catch (NumberFormatException exception) {
            return true;
        }

        return Integer.parseInt(stringNumber) <= 0;
    }

    public static boolean isNotDate(String date) {
        if (date == null)
            return true;

        try {
            java.sql.Date.valueOf(date);
        } catch (Exception exception) {
            return true;
        }

        return false;
    }

    public static void dialogMessage(String message, boolean success, String title) {
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        if (success) {
            label.setForeground(new Color(92, 184, 92));
        } else {
            label.setForeground(new Color(217, 83, 79));
        }

        JOptionPane.showOptionDialog(null, label, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }
}
