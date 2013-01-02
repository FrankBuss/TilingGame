package game

object HersheyFont {
  //
  // HersheyFont, based on Hershey.C, converted to Scala by Frank Buss 1/2/2013
  // hersheyDrawString is not thread safe
  //
  // Hershey.C
  // extracted from the hershey font
  //  
  // Charles Schwieters 6/14/99
  //
  //
  // font info:
  //
  //Peter Holzmann, Octopus Enterprises
  //USPS: 19611 La Mar Court, Cupertino, CA 95014
  //UUCP: {hplabs!hpdsd,pyramid}!octopus!pete
  //Phone: 408/996-7746
  //
  //This distribution is made possible through the collective encouragement
  //of the Usenet Font Consortium, a mailing list that sprang to life to get
  //this accomplished and that will now most likely disappear into the mists
  //of time... Thanks are especially due to Jim Hurt, who provided the packed
  //font data for the distribution, along with a lot of other help.
  //
  //This file describes the Hershey Fonts in general, along with a description of
  //the other files in this distribution and a simple re-distribution restriction.
  //
  //USE RESTRICTION:
  //  This distribution of the Hershey Fonts may be used by anyone for
  //  any purpose, commercial or otherwise, providing that:
  //    1. The following acknowledgements must be distributed with
  //      the font data:
  //      - The Hershey Fonts were originally created by Dr.
  //        A. V. Hershey while working at the U. S.
  //        National Bureau of Standards.
  //      - The format of the Font data in this distribution
  //        was originally created by
  //          James Hurt
  //          Cognition, Inc.
  //          900 Technology Park Drive
  //          Billerica, MA 01821
  //          (mit-eddie!ci-dandelion!hurt)
  //    2. The font data in this distribution may be converted into
  //      any other format *EXCEPT* the format distributed by
  //      the U.S. NTIS (which organization holds the rights
  //      to the distribution and use of the font data in that
  //      particular format). Not that anybody would really
  //      *want* to use their format... each point is described
  //      in eight bytes as "xxx yyy:", where xxx and yyy are
  //      the coordinate values as ASCII numbers.
  //
  //*PLEASE* be reassured: The legal implications of NTIS' attempt to control
  //a particular form of the Hershey Fonts *are* troubling. HOWEVER: We have
  //been endlessly and repeatedly assured by NTIS that they do not care what
  //we do with our version of the font data, they do not want to know about it,
  //they understand that we are distributing this information all over the world,
  //etc etc etc... but because it isn't in their *exact* distribution format, they
  //just don't care!!! So go ahead and use the data with a clear conscience! (If
  //you feel bad about it, take a smaller deduction for something on your taxes
  //next week...)
  //
  //The Hershey Fonts:
  //  - are a set of more than 2000 glyph (symbol) descriptions in vector
  //    ( &lt;x,y&gt; point-to-point ) format
  //  - can be grouped as almost 20 'occidental' (english, greek,
  //    cyrillic) fonts, 3 or more 'oriental' (Kanji, Hiragana,
  //    and Katakana) fonts, and a few hundred miscellaneous
  //    symbols (mathematical, musical, cartographic, etc etc)
  //  - are suitable for typographic quality output on a vector device
  //    (such as a plotter) when used at an appropriate scale.
  //  - were digitized by Dr. A. V. Hershey while working for the U.S.
  //    Government National Bureau of Standards (NBS).
  //  - are in the public domain, with a few caveats:
  //    - They are available from NTIS (National Technical Info.
  //      Service) in a computer-readable from which is *not*
  //      in the public domain. This format is described in
  //      a hardcopy publication "Tables of Coordinates for
  //      Hershey's Repertory of Occidental Type Fonts and
  //      Graphic Symbols" available from NTIS for less than
  //      $20 US (phone number +1 703 487 4763).
  //    - NTIS does not care about and doesn't want to know about
  //      what happens to Hershey Font data that is not
  //      distributed in their exact format.
  //    - This distribution is not in the NTIS format, and thus is
  //      only subject to the simple restriction described
  //      at the top of this file.
  //

  var hersheyFontData = Array(
    "JZ",
    "MWRFRT RRYQZR[SZRY",
    "JZNFNM RVFVM",
    "H]SBLb RYBRb RLOZO RKUYU",
    "H\\PBP_ RTBT_ RYIWGTFPFMGKIKKLMMNOOUQWRXSYUYXWZT[P[MZKX",
    "F^[FI[ RNFPHPJOLMMKMIKIIJGLFNFPGSHVHYG[F RWTUUTWTYV[X[ZZ[X[VYTWT",
    "E_\\O\\N[MZMYNXPVUTXRZP[L[JZIYHWHUISJRQNRMSKSIRGPFNGMIMKNNPQUXWZY[",
    "MWRHQGRFSGSIRKQL",
    "KYVBTDRGPKOPOTPYR]T`Vb",
    "KYNBPDRGTKUPUTTYR]P`Nb",
    "JZRLRX RMOWU RWOMU",
    "E_RIR[ RIR[R",
    "NVSWRXQWRVSWSYQ[",
    "E_IR[R",
    "NVRVQWRXSWRV",
    "G][BIb",
    "H\\QFNGLJKOKRLWNZQ[S[VZXWYRYOXJVGSFQF",
    "H\\NJPISFS[",
    "H\\LKLJMHNGPFTFVGWHXJXLWNUQK[Y[",
    "H\\MFXFRNUNWOXPYSYUXXVZS[P[MZLYKW",
    "H\\UFKTZT RUFU[",
    "H\\WFMFLOMNPMSMVNXPYSYUXXVZS[P[MZLYKW",
    "H\\XIWGTFRFOGMJLOLTMXOZR[S[VZXXYUYTXQVOSNRNOOMQLT",
    "H\\YFO[ RKFYF",
    "H\\PFMGLILKMMONSOVPXRYTYWXYWZT[P[MZLYKWKTLRNPQOUNWMXKXIWGTFPF",
    "H\\XMWPURRSQSNRLPKMKLLINGQFRFUGWIXMXRWWUZR[P[MZLX",
    "NVROQPRQSPRO RRVQWRXSWRV",
    "NVROQPRQSPRO RSWRXQWRVSWSYQ[",
    "F^ZIJRZ[",
    "E_IO[O RIU[U",
    "F^JIZRJ[",
    "I[LKLJMHNGPFTFVGWHXJXLWNVORQRT RRYQZR[SZRY",
    "E`WNVLTKQKOLNMMPMSNUPVSVUUVS RQKOMNPNSOUPV RWKVSVUXVZV\\T]Q]O\\L[J",
    "I[RFJ[ RRFZ[ RMTWT",
    "G\\KFK[ RKFTFWGXHYJYLXNWOTP RKPTPWQXRYTYWXYWZT[K[",
    "H]ZKYIWGUFQFOGMILKKNKSLVMXOZQ[U[WZYXZV",
    "G\\KFK[ RKFRFUGWIXKYNYSXVWXUZR[K[",
    "H[LFL[ RLFYF RLPTP RL[Y[",
    "HZLFL[ RLFYF RLPTP",
    "H]ZKYIWGUFQFOGMILKKNKSLVMXOZQ[U[WZYXZVZS RUSZS",
    "G]KFK[ RYFY[ RKPYP",
    "NVRFR[",
    "JZVFVVUYTZR[P[NZMYLVLT",
    "G\\KFK[ RYFKT RPOY[",
    "HYLFL[ RL[X[",
    "F^JFJ[ RJFR[ RZFR[ RZFZ[",
    "G]KFK[ RKFY[ RYFY[",
    "G]PFNGLIKKJNJSKVLXNZP[T[VZXXYVZSZNYKXIVGTFPF",
    "G\\KFK[ RKFTFWGXHYJYMXOWPTQKQ",
    "G]PFNGLIKKJNJSKVLXNZP[T[VZXXYVZSZNYKXIVGTFPF RSWY]",
    "G\\KFK[ RKFTFWGXHYJYLXNWOTPKP RRPY[",
    "H\\YIWGTFPFMGKIKKLMMNOOUQWRXSYUYXWZT[P[MZKX",
    "JZRFR[ RKFYF",
    "G]KFKULXNZQ[S[VZXXYUYF",
    "I[JFR[ RZFR[",
    "F^HFM[ RRFM[ RRFW[ R\\FW[",
    "H\\KFY[ RYFK[",
    "I[JFRPR[ RZFRP",
    "H\\YFK[ RKFYF RK[Y[",
    "KYOBOb RPBPb ROBVB RObVb",
    "KYKFY^",
    "KYTBTb RUBUb RNBUB RNbUb",
    "JZRDJR RRDZR",
    "I[Ib[b",
    "NVSKQMQORPSORNQO",
    "I\\XMX[ RXPVNTMQMONMPLSLUMXOZQ[T[VZXX",
    "H[LFL[ RLPNNPMSMUNWPXSXUWXUZS[P[NZLX",
    "I[XPVNTMQMONMPLSLUMXOZQ[T[VZXX",
    "I\\XFX[ RXPVNTMQMONMPLSLUMXOZQ[T[VZXX",
    "I[LSXSXQWOVNTMQMONMPLSLUMXOZQ[T[VZXX",
    "MYWFUFSGRJR[ ROMVM",
    "I\\XMX]W`VaTbQbOa RXPVNTMQMONMPLSLUMXOZQ[T[VZXX",
    "I\\MFM[ RMQPNRMUMWNXQX[",
    "NVQFRGSFREQF RRMR[",
    "MWRFSGTFSERF RSMS^RaPbNb",
    "IZMFM[ RWMMW RQSX[",
    "NVRFR[",
    "CaGMG[ RGQJNLMOMQNRQR[ RRQUNWMZM\\N]Q][",
    "I\\MMM[ RMQPNRMUMWNXQX[",
    "I\\QMONMPLSLUMXOZQ[T[VZXXYUYSXPVNTMQM",
    "H[LMLb RLPNNPMSMUNWPXSXUWXUZS[P[NZLX",
    "I\\XMXb RXPVNTMQMONMPLSLUMXOZQ[T[VZXX",
    "KXOMO[ ROSPPRNTMWM",
    "J[XPWNTMQMNNMPNRPSUTWUXWXXWZT[Q[NZMX",
    "MYRFRWSZU[W[ ROMVM",
    "I\\MMMWNZP[S[UZXW RXMX[",
    "JZLMR[ RXMR[",
    "G]JMN[ RRMN[ RRMV[ RZMV[",
    "J[MMX[ RXMM[",
    "JZLMR[ RXMR[P_NaLbKb",
    "J[XMM[ RMMXM RM[X[",
    "KYTBRCQDPFPHQJRKSMSOQQ RRCQEQGRISJTLTNSPORSTTVTXSZR[Q]Q_Ra RQSSU",
    "NVRBRb",
    "KYPBRCSDTFTHSJRKQMQOSQ RRCSESGRIQJPLPNQPURQTPVPXQZR[S]S_Ra RSSQU",
    "F^IUISJPLONOPPTSVTXTZS[Q RISJQLPNPPQTTVUXUZT[Q[O",
    "JZJFJ[K[KFLFL[M[MFNFN[O[OFPFP[Q[QFRFR[S[SFTFT[U[UFVFV[W[WFXFX[Y[YFZFZ[")

  // note the arbitrary scaling
  private def h2double(c: Char): Double = scale * (c - 'R')

  private var scale: Double = 1
  private var x0: Double = 0
  private var y0: Double = 0

  // hersheyDrawLetter() interprets the instructions from the array
  // for that letter and renders the letter with line segments.
  private def hersheyDrawLetter(ch: Char) = {
    var c = ch.toInt - 32
    if (c < 0) {
      c = 0
    } else if (c >= hersheyFontData.length) {
      c = 0
    }
    val cp = hersheyFontData(c)
    val lm: Double = h2double(cp.charAt(0))
    val rm: Double = h2double(cp.charAt(1))
    x0 -= lm
    var x1: Double = x0
    var y1: Double = y0
    var startLinestrip = true
    var lines = List[Line]()
    for (i <- 2 until cp.length() by 2) {
      if (cp.charAt(i) == ' ' && cp.charAt(i + 1) == 'R') {
        startLinestrip = true
      } else {
        val x: Double = h2double(cp.charAt(i))
        val y: Double = h2double(cp.charAt(i + 1))
        if (startLinestrip) {
          startLinestrip = false
          x1 = x0 + x
          y1 = y0 + y
        } else {
          val x2: Double = x0 + x
          val y2: Double = y0 + y
          lines ::= new Line(Vector2d(x1, y1), Vector2d(x2, y2))
          x1 = x2
          y1 = y2
        }
      }
    }
    x0 += rm
    lines
  }

  def drawString(text: String, x: Double, y: Double, scale: Double) = {
    x0 = x
    y0 = y
    this.scale = scale
    var lines = List[Line]()
    text.foreach(ch => lines ++= hersheyDrawLetter(ch))
    lines
  }
}