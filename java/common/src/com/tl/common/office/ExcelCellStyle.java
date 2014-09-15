package com.tl.common.office;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.tl.common.office.ExcelDataFormat;

public class ExcelCellStyle {
	public enum AlignStyle{
		GENERAL(HSSFCellStyle.ALIGN_GENERAL) {
			@Override
			public HorizontalAlignment XSSFValue() {
				return HorizontalAlignment.GENERAL;
			}
		},
		LEFT(HSSFCellStyle.ALIGN_LEFT) {
			@Override
			public HorizontalAlignment XSSFValue() {
				return HorizontalAlignment.LEFT;
			}
		},
		CENTER(HSSFCellStyle.ALIGN_CENTER) {
			@Override
			public HorizontalAlignment XSSFValue() {
				return HorizontalAlignment.CENTER;
			}
		},
		RIGHT(HSSFCellStyle.ALIGN_RIGHT) {
			@Override
			public HorizontalAlignment XSSFValue() {
				return HorizontalAlignment.RIGHT;
			}
		},
		FILL(HSSFCellStyle.ALIGN_FILL) {
			@Override
			public HorizontalAlignment XSSFValue() {
				return HorizontalAlignment.FILL;
			}
		},
		JUSTIFY(HSSFCellStyle.ALIGN_JUSTIFY) {
			@Override
			public HorizontalAlignment XSSFValue() {
				return HorizontalAlignment.JUSTIFY;
			}
		},
		CENTER_SELECTION(HSSFCellStyle.ALIGN_CENTER_SELECTION) {
			@Override
			public HorizontalAlignment XSSFValue() {
				return HorizontalAlignment.CENTER_SELECTION;
			}
		};
		
		private short HSSFValue;
		private AlignStyle(short HSSFValue){
			this.HSSFValue = HSSFValue;
		}
		
		public short HSSFValue(){
			return HSSFValue;
		}
		
		public abstract HorizontalAlignment XSSFValue();
	}
	
	public enum VAlignStyle{
		TOP(HSSFCellStyle.VERTICAL_TOP) {
			@Override
			public VerticalAlignment XSSFValue() {
				return VerticalAlignment.TOP;
			}
		},
		CENTER(HSSFCellStyle.ALIGN_CENTER) {
			@Override
			public VerticalAlignment XSSFValue() {
				return VerticalAlignment.CENTER;
			}
		},
		BOTTOM(HSSFCellStyle.VERTICAL_BOTTOM) {
			@Override
			public VerticalAlignment XSSFValue() {
				return VerticalAlignment.BOTTOM;
			}
		},
		JUSTIFY(HSSFCellStyle.VERTICAL_JUSTIFY) {
			@Override
			public VerticalAlignment XSSFValue() {
				return VerticalAlignment.JUSTIFY;
			}
		};
		
		private short HSSFVAlign;
		private VAlignStyle(short HSSFVAlign){
			this.HSSFVAlign = HSSFVAlign;
		}
		
		public short HSSFValue(){
			return HSSFVAlign;
		}
		public abstract VerticalAlignment XSSFValue();
	}
	
	public enum BorderStyle{
		NONE(HSSFCellStyle.BORDER_NONE) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.NONE;
			}
		},
		THIN(HSSFCellStyle.BORDER_THIN) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.THIN;
			}
		},
		MEDIUM(HSSFCellStyle.BORDER_MEDIUM) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
			}
		},
		DASHED(HSSFCellStyle.BORDER_DASHED) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.DASHED;
			}
		},
		DOTTED(HSSFCellStyle.BORDER_DOTTED) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.DOTTED;
			}
		},
		THICK(HSSFCellStyle.BORDER_THICK) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.THICK;
			}
		},
		DOUBLE(HSSFCellStyle.BORDER_DOUBLE) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.DOUBLE;
			}
		},
		HAIR(HSSFCellStyle.BORDER_HAIR) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.HAIR;
			}
		},
		MEDIUM_DASHED(HSSFCellStyle.BORDER_MEDIUM_DASHED) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.MEDIUM_DASHED;
			}
		},
		DASH_DOT(HSSFCellStyle.BORDER_DASH_DOT) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.DASH_DOT;
			}
		},
		MEDIUM_DASH_DOT(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.MEDIUM_DASH_DOT;
			}
		},
		DASH_DOT_DOT(HSSFCellStyle.BORDER_DASH_DOT_DOT) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.DASH_DOT_DOT;
			}
		},
		MEDIUM_DASH_DOT_DOT(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.MEDIUM_DASH_DOT_DOTC;
			}
		},
		SLANTED_DASH_DOT(HSSFCellStyle.BORDER_SLANTED_DASH_DOT) {
			@Override
			public org.apache.poi.ss.usermodel.BorderStyle XSSFValue() {
				return org.apache.poi.ss.usermodel.BorderStyle.SLANTED_DASH_DOT;
			}
		};
		
		private short HSSFValue;
		private BorderStyle(short HSSFValue){
			this.HSSFValue = HSSFValue;
		}
		
		public short HSSFValue(){
			return HSSFValue;
		}
		
		public abstract org.apache.poi.ss.usermodel.BorderStyle XSSFValue();
	}
	
	public enum FillPatternStyle{
		NO_FILL(HSSFCellStyle.NO_FILL) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.NO_FILL;
			}
		},
		SOLID_FOREGROUND(HSSFCellStyle.SOLID_FOREGROUND) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.SOLID_FOREGROUND;
			}
		},
		FINE_DOTS(HSSFCellStyle.FINE_DOTS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.FINE_DOTS;
			}
		},
		ALT_BARS(HSSFCellStyle.ALT_BARS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.ALT_BARS;
			}
		},
		SPARSE_DOTS(HSSFCellStyle.SPARSE_DOTS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.SPARSE_DOTS;
			}
		},
		THICK_HORZ_BANDS(HSSFCellStyle.THICK_HORZ_BANDS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.THICK_HORZ_BANDS;
			}
		},
		THICK_VERT_BANDS(HSSFCellStyle.THICK_VERT_BANDS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.THICK_VERT_BANDS;
			}
		},
		THICK_BACKWARD_DIAG(HSSFCellStyle.THICK_BACKWARD_DIAG) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.THICK_BACKWARD_DIAG;
			}
		},
		THICK_FORWARD_DIAG(HSSFCellStyle.THICK_FORWARD_DIAG) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.THICK_FORWARD_DIAG;
			}
		},
		BIG_SPOTS(HSSFCellStyle.BIG_SPOTS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.BIG_SPOTS;
			}
		},
		BRICKS(HSSFCellStyle.BRICKS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.BRICKS;
			}
		},
		THIN_HORZ_BANDS(HSSFCellStyle.THIN_HORZ_BANDS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.THIN_HORZ_BANDS;
			}
		},
		THIN_VERT_BANDS(HSSFCellStyle.THIN_VERT_BANDS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.THIN_VERT_BANDS;
			}
		},
		THIN_BACKWARD_DIAG(HSSFCellStyle.THIN_BACKWARD_DIAG) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.THIN_BACKWARD_DIAG;
			}
		},
		THIN_FORWARD_DIAG(HSSFCellStyle.THIN_FORWARD_DIAG) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.THIN_FORWARD_DIAG;
			}
		},
		SQUARES(HSSFCellStyle.SQUARES) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.SQUARES;
			}
		},
		DIAMONDS(HSSFCellStyle.DIAMONDS) {
			@Override
			public FillPatternType XSSFValue() {
				return FillPatternType.DIAMONDS;
			}
		};
		
		private short HSSFValue;
		private FillPatternStyle(short HSSFValue){
			this.HSSFValue = HSSFValue;
		}
		
		public short HSSFValue(){
			return HSSFValue;
		}
		
		public abstract FillPatternType XSSFValue();
	}
	
	public enum Color{
		AUTOMATIC(HSSFColor.AUTOMATIC.index),
		BLACK(HSSFColor.BLACK.index),
		BROWN(HSSFColor.BROWN.index),
		OLIVE_GREEN(HSSFColor.OLIVE_GREEN.index),
		DARK_GREEN(HSSFColor.DARK_GREEN.index),
		DARK_TEAL(HSSFColor.DARK_TEAL.index),
		DARK_BLUE(HSSFColor.DARK_BLUE.index),
		INDIGO(HSSFColor.INDIGO.index),
		GREY_80_PERCENT(HSSFColor.GREY_80_PERCENT.index),
		ORANGE(HSSFColor.ORANGE.index),
		DARK_YELLOW(HSSFColor.DARK_YELLOW.index),
		GREEN(HSSFColor.GREEN.index),
		TEAL(HSSFColor.TEAL.index),
		BLUE(HSSFColor.BLUE.index),
		BLUE_GREY(HSSFColor.BLUE_GREY.index),
		GREY_50_PERCENT(HSSFColor.GREY_50_PERCENT.index),
		RED(HSSFColor.RED.index),
		LIGHT_ORANGE(HSSFColor.LIGHT_ORANGE.index),
		LIME(HSSFColor.LIME.index),
		SEA_GREEN(HSSFColor.SEA_GREEN.index),
		AQUA(HSSFColor.AQUA.index),
		LIGHT_BLUE(HSSFColor.LIGHT_BLUE.index),
		VIOLET(HSSFColor.VIOLET.index),
		GREY_40_PERCENT(HSSFColor.GREY_40_PERCENT.index),
		PINK(HSSFColor.PINK.index),
		GOLD(HSSFColor.GOLD.index),
		YELLOW(HSSFColor.YELLOW.index),
		BRIGHT_GREEN(HSSFColor.BRIGHT_GREEN.index),
		TURQUOISE(HSSFColor.TURQUOISE.index),
		DARK_RED(HSSFColor.DARK_RED.index),
		SKY_BLUE(HSSFColor.SKY_BLUE.index),
		PLUM(HSSFColor.PLUM.index),
		GREY_25_PERCENT(HSSFColor.GREY_25_PERCENT.index),
		ROSE(HSSFColor.ROSE.index),
		LIGHT_YELLOW(HSSFColor.LIGHT_YELLOW.index),
		LIGHT_GREEN(HSSFColor.LIGHT_GREEN.index),
		LIGHT_TURQUOISE(HSSFColor.LIGHT_TURQUOISE.index),
		PALE_BLUE(HSSFColor.PALE_BLUE.index),
		LAVENDER(HSSFColor.LAVENDER.index),
		WHITE(HSSFColor.WHITE.index),
		CORNFLOWER_BLUE(HSSFColor.CORNFLOWER_BLUE.index),
		LEMON_CHIFFON(HSSFColor.LEMON_CHIFFON.index),
		MAROON(HSSFColor.MAROON.index),
		ORCHID(HSSFColor.ORCHID.index),
		CORAL(HSSFColor.CORAL.index),
		ROYAL_BLUE(HSSFColor.ROYAL_BLUE.index),
		LIGHT_CORNFLOWER_BLUE(HSSFColor.LIGHT_CORNFLOWER_BLUE.index),
		TAN(HSSFColor.TAN.index);
		
		private short index;
		private Color(short index){
			this.index = index;
		}
		
		public short index(){
			return index;
		}
	}
	
	public enum BoldweightStyle{
		NORMAL(Font.BOLDWEIGHT_NORMAL),
		BOLD(Font.BOLDWEIGHT_BOLD);
		private short index;
		private BoldweightStyle(int index){
			this.index = (short)index;
		}
		
		public short index(){
			return index;
		}
	}
	
	public enum Charset{
		ANSI(Font.ANSI_CHARSET),
		DEFAULT(Font.DEFAULT_CHARSET),
		SYMBOL(Font.SYMBOL_CHARSET);
		private short index;
		private Charset(int index){
			this.index = (short)index;
		}
		
		public short index(){
			return index;
		}
	}
	
	public enum FontTypeOffset{
		NONE(Font.SS_NONE),
		SUPER(Font.SS_SUPER),
		SUB(Font.SS_SUB);
		
		private short index;
		private FontTypeOffset(int index){
			this.index = (short)index;
		}
		
		public short index(){
			return index;
		}
	}
	
	public enum FontUnderline{
		NONE(Font.U_NONE),
		DOUBLE(Font.U_DOUBLE),
		DOUBLE_ACCOUNTING(Font.U_DOUBLE_ACCOUNTING),
		SINGLE(Font.U_SINGLE),
		SINGLE_ACCOUNTING(Font.U_SINGLE_ACCOUNTING);
		
		private short index;
		private FontUnderline(int index){
			this.index = (short)index;
		}
		
		public short index(){
			return index;
		}
	}
	
	private AlignStyle align = AlignStyle.GENERAL;
	private VAlignStyle valign = VAlignStyle.CENTER;
	private BorderStyle borderBottom = BorderStyle.NONE;
	private Color borderBottomColor = Color.AUTOMATIC;
	private BorderStyle borderTop = BorderStyle.NONE;
	private Color borderTopColor =  Color.AUTOMATIC;
	private BorderStyle borderLeft = BorderStyle.NONE;
	private Color borderLeftColor =  Color.AUTOMATIC;
	private BorderStyle borderRight = BorderStyle.NONE;
	private Color borderRightColor =  Color.AUTOMATIC;
	private FillPatternStyle fillPattern;
	private Color backgroundColor =  Color.AUTOMATIC;
	private Color foregroundColor =  Color.AUTOMATIC;
	private ExcelDataFormat format = ExcelDataFormat.General;
	private boolean isWrapText = false;
	
	private BoldweightStyle fontBoldweight = BoldweightStyle.NORMAL;
	private Charset fontCharset = Charset.DEFAULT;
	private Color fontColor =  Color.AUTOMATIC;
	private Short fontHeight = 12;
	private String fontName = "ËÎÌו";
	private boolean fontItalic = false;
	private boolean fontStrikeout = false;
	private FontTypeOffset fontTypeOffset = FontTypeOffset.NONE;
	private FontUnderline fontUnderline = FontUnderline.NONE;
	public AlignStyle getAlign() {
		return align;
	}
	public void setAlign(AlignStyle align) {
		this.align = align;
	}
	public VAlignStyle getValign() {
		return valign;
	}
	public void setValign(VAlignStyle valign) {
		this.valign = valign;
	}
	public BorderStyle getBorderBottom() {
		return borderBottom;
	}
	public void setBorderBottom(BorderStyle borderBottom) {
		this.borderBottom = borderBottom;
	}
	public Color getBorderBottomColor() {
		return borderBottomColor;
	}
	public void setBorderBottomColor(Color borderBottomColor) {
		this.borderBottomColor = borderBottomColor;
	}
	public BorderStyle getBorderTop() {
		return borderTop;
	}
	public void setBorderTop(BorderStyle borderTop) {
		this.borderTop = borderTop;
	}
	public Color getBorderTopColor() {
		return borderTopColor;
	}
	public void setBorderTopColor(Color borderTopColor) {
		this.borderTopColor = borderTopColor;
	}
	public BorderStyle getBorderLeft() {
		return borderLeft;
	}
	public void setBorderLeft(BorderStyle borderLeft) {
		this.borderLeft = borderLeft;
	}
	public Color getBorderLeftColor() {
		return borderLeftColor;
	}
	public void setBorderLeftColor(Color borderLeftColor) {
		this.borderLeftColor = borderLeftColor;
	}
	public BorderStyle getBorderRight() {
		return borderRight;
	}
	public void setBorderRight(BorderStyle borderRight) {
		this.borderRight = borderRight;
	}
	public Color getBorderRightColor() {
		return borderRightColor;
	}
	public void setBorderRightColor(Color borderRightColor) {
		this.borderRightColor = borderRightColor;
	}
	public FillPatternStyle getFillPattern() {
		return fillPattern;
	}
	public void setFillPattern(FillPatternStyle fillPattern) {
		this.fillPattern = fillPattern;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Color getForegroundColor() {
		return foregroundColor;
	}
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	public ExcelDataFormat getFormat() {
		return format;
	}
	public void setFormat(ExcelDataFormat format) {
		this.format = format;
	}
	public void setFormat(short format) {
		for (ExcelDataFormat f : ExcelDataFormat.values()) {
			if(f.getFormat() == format){
				this.format = f;
				return;
			}
		}
		this.format = ExcelDataFormat.General;
	}
	public boolean isWrapText() {
		return isWrapText;
	}
	public void setWrapText(boolean isWrapText) {
		this.isWrapText = isWrapText;
	}
	public BoldweightStyle getFontBoldweight() {
		return fontBoldweight;
	}
	public void setFontBoldweight(BoldweightStyle fontBoldweight) {
		this.fontBoldweight = fontBoldweight;
	}
	public Charset getFontCharset() {
		return fontCharset;
	}
	public void setFontCharset(Charset fontCharset) {
		this.fontCharset = fontCharset;
	}
	public Color getFontColor() {
		return fontColor;
	}
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
	public Short getFontHeight() {
		return fontHeight;
	}
	public void setFontHeight(Short fontHeight) {
		this.fontHeight = fontHeight;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public boolean isFontItalic() {
		return fontItalic;
	}
	public void setFontItalic(boolean fontItalic) {
		this.fontItalic = fontItalic;
	}
	public boolean isFontStrikeout() {
		return fontStrikeout;
	}
	public void setFontStrikeout(boolean fontStrikeout) {
		this.fontStrikeout = fontStrikeout;
	}
	public FontTypeOffset getFontTypeOffset() {
		return fontTypeOffset;
	}
	public void setFontTypeOffset(FontTypeOffset fontTypeOffset) {
		this.fontTypeOffset = fontTypeOffset;
	}
	public FontUnderline getFontUnderline() {
		return fontUnderline;
	}
	public void setFontUnderline(FontUnderline fontUnderline) {
		this.fontUnderline = fontUnderline;
	}
	
	
}
