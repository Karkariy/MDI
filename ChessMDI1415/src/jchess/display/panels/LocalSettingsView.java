/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchess.display.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jchess.core.Game;
import jchess.core.moves.Moves;
import jchess.display.windows.DrawLocalSettings;
import jchess.utils.Settings;

/**
 *
 * @author Mateusz Lach ( matlak, msl )
 */
public class LocalSettingsView extends JPanel implements ActionListener
{
    private JCheckBox isUpsideDown;
    
    private JCheckBox isDisplayLegalMovesEnabled;
    
    private JCheckBox isRenderLabelsEnabled;    
    
    private JCheckBox timeGame;
    private JComboBox time4Game;
     
    private GridBagConstraints gbc;
    
    private GridBagLayout gbl;
    
    public JTextArea CommentZone;
    
    private Game game;
    
    public LocalSettingsView(Game game)
    {
        this.game = game;
        
     // ajout du champ texte
        this.CommentZone = new JTextArea();
        this.CommentZone.setText("com");
        this.CommentZone.setEditable(true);

        
        // initialisation du temps 
        this.gbc = new GridBagConstraints();
        this.gbl = new GridBagLayout();
        this.timeGame = new JCheckBox(Settings.lang("time_game_min"));
        this.time4Game = new JComboBox(DrawLocalSettings.times);
        
        
        // ajout du checkbox dans la vue
        this.timeGame.setSize(this.timeGame.getHeight(), this.getWidth());
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.insets = new Insets(3, 3, 3, 3);
        this.gbl.setConstraints(timeGame, gbc);
        this.add(timeGame);
        timeGame.addActionListener(this);

        
        //Ajout du comboBox dans la vue
        this.time4Game.setSize(this.time4Game.getHeight(), this.getWidth());
        this.gbc.gridx = 1;
        this.gbc.gridy = 3;
        this.gbc.insets = new Insets(3, 3, 3, 3);
        this.gbl.setConstraints(time4Game, gbc);
        this.add(time4Game);
        time4Game.addActionListener(this);
        
        //this.CommentZone.setBounds(200, 200 , 200 , 200);
        this.CommentZone.setLocation(50, 50);
        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.gbc.insets = new Insets(3, 3, 3, 3);
        this.gbl.setConstraints(CommentZone, gbc);
        this.add(CommentZone);

        this.setLayout(gbl);

        initUpsideDownControl();
        initDisplayLegalMovesControl();
        initRenderLabelsControl();
        //initTimeControl(game);
        refreshCheckBoxesState();
    }

    
   public boolean initTimeControl()
    {
	   boolean fait = false;
	   
        if (this.timeGame.isSelected()) //if timeGame is checked
        {
        	fait = true;
            String value = DrawLocalSettings.times[this.time4Game.getSelectedIndex()];//set time for game
            Integer val = new Integer(value);
            Settings sett = game.getSettings();
            sett.setTimeForGame((int) val * 60);
            game.setSettings(sett);//set time for game and mult it to seconds
            game.getGameClock().setTimes(sett.getTimeForGame(), sett.getTimeForGame());
        } 
        
        DrawLocalSettings.LOG.debug("this.time4Game.getActionCommand(): " + this.time4Game.getActionCommand());
        
        timeGame.addActionListener(this);
        return fait;
    }
    
    private void initUpsideDownControl()
    {
        this.isUpsideDown = new JCheckBox();
        this.isUpsideDown.setText(Settings.lang("upside_down"));
        this.isUpsideDown.setSize(this.isUpsideDown.getHeight(), this.getWidth());
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.insets = new Insets(3, 3, 3, 3);
        this.gbl.setConstraints(isUpsideDown, gbc);
        this.add(isUpsideDown);
        
        isUpsideDown.addActionListener(this);
    }
    
    private void initDisplayLegalMovesControl()
    {
        this.isDisplayLegalMovesEnabled = new JCheckBox();
        this.isDisplayLegalMovesEnabled.setText(Settings.lang("display_legal_moves"));     
        
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbl.setConstraints(isDisplayLegalMovesEnabled, gbc);
        this.add(isDisplayLegalMovesEnabled);
        
        isDisplayLegalMovesEnabled.addActionListener(this);        
    }
    
    private void initRenderLabelsControl()
    {
        this.isRenderLabelsEnabled = new JCheckBox();
        this.isRenderLabelsEnabled.setText(Settings.lang("display_labels"));     
        
        this.gbc.gridx = 0;
        this.gbc.gridy = 2;
        this.gbl.setConstraints(isRenderLabelsEnabled, gbc);
        this.add(isRenderLabelsEnabled);
        
        isRenderLabelsEnabled.addActionListener(this);        
    }
        
    private void refreshCheckBoxesState()
    {
        if (isInitiatedCorrectly())
        {
            isUpsideDown.setSelected(game.getSettings().isUpsideDown());
            isDisplayLegalMovesEnabled.setSelected(game.getSettings().isDisplayLegalMovesEnabled());
            isRenderLabelsEnabled.setSelected(game.getSettings().isRenderLabels());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JCheckBox clickedComponent = (JCheckBox) e.getSource();
        if (clickedComponent == isUpsideDown)
        {
            game.getSettings().setUpsideDown(isUpsideDown.isSelected());
        } 
        else if (clickedComponent == isDisplayLegalMovesEnabled)
        {
            game.getSettings().setDisplayLegalMovesEnabled(isDisplayLegalMovesEnabled.isSelected());
        }
        else if (clickedComponent == isRenderLabelsEnabled) 
        {
            game.getSettings().setRenderLabels(isRenderLabelsEnabled.isSelected());
            game.resizeGame();
        }
        
        game.repaint();
    }
    
    @Override
    public void repaint()
    {
        refreshCheckBoxesState();
        super.repaint();
    }

    private boolean isInitiatedCorrectly()
    {
        return null != isUpsideDown && null != isDisplayLegalMovesEnabled
                && null != isRenderLabelsEnabled;
    }
}
