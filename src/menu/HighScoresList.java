package menu;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoresList extends JPanel {

    private final JList<String> highScoresList;
    private final DefaultListModel<String> listModel;

    public HighScoresList() {
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        highScoresList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(highScoresList);
        add(scrollPane, BorderLayout.CENTER);
        loadHighScores();
    }

    private void loadHighScores() {
        try {
            List<PlayerResult> results = PlayerSavingMechanics.readResults();

            //SORTING HIGHEST TO LOWEST
            results.sort((r1, r2) -> Integer.compare(r2.getPoints(), r1.getPoints()));

            //CLEAR TO ADD ONLY SORTED SCORES
            listModel.clear();

            //ADDING TO LIST
            for (PlayerResult result : results) {
                listModel.addElement(result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
