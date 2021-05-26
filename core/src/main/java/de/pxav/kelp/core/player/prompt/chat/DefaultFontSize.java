package de.pxav.kelp.core.player.prompt.chat;

/**
 * This class is used to decide how big different chars appear in the chat,
 * which is important for deciding where to put a message if it should be
 * centered.
 *
 * This class is written by SirSpoodles from SpigotMC who provided a method for
 * sending centered chat messages. Link to the corresponding spigot page:
 * https://www.spigotmc.org/threads/free-code-sending-perfectly-centered-chat-message.95872/
 *
 * The file has been modified and some further chars have been implemented to
 * be more universal and applicable for more use cases.
 *
 * @author SirSpoodles
 * @author pxav
 */
public enum DefaultFontSize {

  A('A', 5),
  a('a', 5),
  B('B', 5),
  b('b', 5),
  C('C', 5),
  c('c', 5),
  D('D', 5),
  d('d', 5),
  E('E', 5),
  e('e', 5),
  F('F', 5),
  f('f', 4),
  G('G', 5),
  g('g', 5),
  H('H', 5),
  h('h', 5),
  I('I', 3),
  i('i', 1),
  J('J', 5),
  j('j', 5),
  K('K', 5),
  k('k', 4),
  L('L', 5),
  l('l', 1),
  M('M', 5),
  m('m', 5),
  N('N', 5),
  n('n', 5),
  O('O', 5),
  o('o', 5),
  P('P', 5),
  p('p', 5),
  Q('Q', 5),
  q('q', 5),
  R('R', 5),
  r('r', 5),
  S('S', 5),
  s('s', 5),
  T('T', 5),
  t('t', 4),
  U('U', 5),
  u('u', 5),
  V('V', 5),
  v('v', 5),
  W('W', 5),
  w('w', 5),
  X('X', 5),
  x('x', 5),
  Y('Y', 5),
  y('y', 5),
  Z('Z', 5),
  z('z', 5),
  NUM_1('1', 5),
  NUM_2('2', 5),
  NUM_3('3', 5),
  NUM_4('4', 5),
  NUM_5('5', 5),
  NUM_6('6', 5),
  NUM_7('7', 5),
  NUM_8('8', 5),
  NUM_9('9', 5),
  NUM_0('0', 5),
  EXCLAMATION_POINT('!', 1),
  AT_SYMBOL('@', 6),
  NUM_SIGN('#', 5),
  DOLLAR_SIGN('$', 5),
  PERCENT('%', 5),
  UP_ARROW('^', 5),
  AMPERSAND('&', 5),
  ASTERISK('*', 5),
  LEFT_PARENTHESIS('(', 4),
  RIGHT_PERENTHESIS(')', 4),
  MINUS('-', 5),
  UNDERSCORE('_', 5),
  PLUS_SIGN('+', 5),
  EQUALS_SIGN('=', 5),
  LEFT_CURL_BRACE('{', 4),
  RIGHT_CURL_BRACE('}', 4),
  LEFT_BRACKET('[', 3),
  RIGHT_BRACKET(']', 3),
  COLON(':', 1),
  SEMI_COLON(';', 1),
  DOUBLE_QUOTE('"', 3),
  SINGLE_QUOTE('\'', 1),
  LEFT_ARROW('<', 4),
  RIGHT_ARROW('>', 4),
  QUESTION_MARK('?', 5),
  SLASH('/', 5),
  BACK_SLASH('\\', 5),
  LINE('|', 1),
  TILDE('~', 5),
  TICK('`', 2),
  PERIOD('.', 1),
  COMMA(',', 1),
  SPACE(' ', 3),
  BLOCK_SOLID('\u2588', 8),
  BLOCK_DARK_SHADE('\u2593', 8),
  BLOCK_MEDIUM_SHADE('\u2592', 8),
  BLOCK_LIGHT_SHADE('\u2591', 8),
  DEFAULT('a', 4);

  private char character;
  private int length;

  DefaultFontSize(char character, int length) {
    this.character = character;
    this.length = length;
  }

  public char getCharacter(){
    return this.character;
  }

  public int getLength(){
    return this.length;
  }

  public int getBoldLength(){
    if(this == DefaultFontSize.SPACE || this.toString().contains("BLOCK")) {
      return this.getLength();
    }
    return this.length + 1;
  }

  public static DefaultFontSize getDefaultFontInfo(char c){
    for(DefaultFontSize dFI : DefaultFontSize.values()){
      if(dFI.getCharacter() == c) return dFI;
    }
    return DefaultFontSize.DEFAULT;
  }

}
