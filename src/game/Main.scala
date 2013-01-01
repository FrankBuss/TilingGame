package game

import javax.swing._
import java.awt.BorderLayout
import java.awt.event._
import java.awt.GridLayout
import scala.util.Random
import java.awt.KeyEventPostProcessor
import java.awt.KeyboardFocusManager

class Main extends JFrame("Tiling Game") {
  // exit application on close frame (doesn't work for Applets)
  try {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  } catch {
    case e: Exception => {}
  }

  // set system look and feel
  try {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  } catch {
    case e: Exception => {}
  }

  // add playfield to the center of the BorderLayout and button toolbar to the bottom
  getContentPane().setLayout(new BorderLayout())
  val game = new Game()
  game.newGame

  getContentPane().add(BorderLayout.CENTER, new GameComponent(game))
  val buttons = new JPanel(new GridLayout(1, 0))
  getContentPane().add(BorderLayout.SOUTH, buttons)

  // define toolbar buttons and anonymous callback functions for each button
  val buttonDefinitions = List(
    ("New Game", () => game.newGame),
    ("Help", () => {
      JOptionPane.showMessageDialog(this,
        "Tiling Game alpha test\n" +
          "Idea: BobCat\n" +
          "Code: Frank Buss\n" +
          "\n" +
          "First click in the field with the mouse, then use the keyboard:\n" +
          "1: place current tile and add thin rhomb\n" +
          "2: place current tile and add thick rhomb\n" +
          "cursor up/down: rotate current tile\n" +
          "cursor left/right: move current tile", "Tiling Game", JOptionPane.INFORMATION_MESSAGE);
    }))

  // add toolbar buttons
  buttonDefinitions.foreach {
    case (label, action) =>
      // create button and add action listener for callback
      val button = new JButton(label)
      button addActionListener new ActionListener {
        def actionPerformed(e: ActionEvent) = action()
      }

      // add button to toolbar
      buttons.add(button)
  }

  // add global key event listener
  val pp = new KeyEventPostProcessor {
    def postProcessKeyEvent(e: KeyEvent): Boolean = {
      if (e.getID == KeyEvent.KEY_PRESSED) {
        e.getKeyCode match {
          case KeyEvent.VK_UP => game.onUp
          case KeyEvent.VK_DOWN => game.onDown
          case KeyEvent.VK_LEFT => game.onLeft
          case KeyEvent.VK_RIGHT => game.onRight
          case KeyEvent.VK_1 => game.onButton1
          case KeyEvent.VK_2 => game.onButton2
          case KeyEvent.VK_3 => game.onButton3
          case KeyEvent.VK_4 => game.onButton4
          case _ => {}
        }
      }
      true
    }
  }

  KeyboardFocusManager.getCurrentKeyboardFocusManager.addKeyEventPostProcessor(pp);

  // layout, resize and show
  pack
  setSize(700, 800)
  setVisible(true)
}

object Main {
  def main(args: Array[String]) {
    // create main window
    val win = new Main()

    // timer for 60 Hz update rate (16 ms)
    val timer = new Timer(16, new ActionListener {
      def actionPerformed(e: ActionEvent) = win.repaint()
    })
    timer.start()
  }
}
