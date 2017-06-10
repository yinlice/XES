function LunarCalendar(){
    this.CalendarData = [0xA4B, 0x5164B, 0x6A5, 0x6D4, 0x415B5, 0x2B6, 0x957, 0x2092F, 0x497, 0x60C96, 0xD4A, 0xEA5, 0x50DA9, 0x5AD, 0x2B6, 0x3126E, 0x92E, 0x7192D, 0xC95, 0xD4A, 0x61B4A, 0xB55, 0x56A, 0x4155B, 0x25D, 0x92D, 0x2192B, 0xA95, 0x71695, 0x6CA, 0xB55, 0x50AB5, 0x4DA, 0xA5B, 0x30A57, 0x52B, 0x8152A, 0xE95, 0x6AA, 0x615AA, 0xAB5, 0x4B6, 0x414AE, 0xA57, 0x526, 0x31D26, 0xD95, 0x70B55, 0x56A, 0x96D, 0x5095D, 0x4AD, 0xA4D, 0x41A4D, 0xD25, 0x81AA5, 0xB54, 0xB6A, 0x612DA, 0x95B, 0x49B, 0x41497, 0xA4B, 0xA164B, 0x6A5, 0x6D4, 0x615B4, 0xAB6, 0x957, 0x5092F, 0x497, 0x64B, 0x30D4A, 0xEA5, 0x80D65, 0x5AC, 0xAB6, 0x5126D, 0x92E, 0xC96, 0x41A95, 0xD4A, 0xDA5, 0x20B55, 0x56A, 0x7155B, 0x25D, 0x92D, 0x5192B, 0xA95, 0xB4A, 0x416AA, 0xAD5, 0x90AB5, 0x4BA, 0xA5B, 0x60A57, 0x52B, 0xA93, 0x40E95];
    this.lunarYear_first = "甲乙丙丁戊己庚辛壬癸";
    this.lunarYear_end = "子丑寅卯辰巳午未申酉戌亥";
    this.lunarMouth = "正二三四五六七八九十冬腊";
    this.lunarDay = "一二三四五六七八九十";
    this.lunarZodiac = "鼠牛虎兔龙蛇马羊猴鸡狗猪";
    this.cYear = this.cMonth = this.cDay = this.TheDate = null;
    this.madd = [0,31,59,90,120,151,181,212,243,273,304,334];

    /**
     * @return {number}
     */
    this.GetBit = function(m, n){
        return (m >> n) & 1;
    };

    this.e2c = function() {
        TheDate = (arguments.length != 3) ? new Date() : new Date(arguments[0], arguments[1], arguments[2]);
        var total, m, n, k;
        var isEnd = false;
        var tmp = TheDate.getYear();/*当前年份*/
        if (tmp < 1900) {
            tmp += 1900;
        }
        total = (tmp - 1921) * 365 + Math.floor((tmp - 1921) / 4) + this.madd[TheDate.getMonth()] + TheDate.getDate() - 38;

        if (TheDate.getYear() % 4 == 0 && TheDate.getMonth() > 1) {
            total++;
        }
        for (m = 0; ; m++) {
            k = (this.CalendarData[m] < 0xfff) ? 11 : 12;
            for (n = k; n >= 0; n--) {
                if (total <= 29 + this.GetBit(this.CalendarData[m], n)) {
                    isEnd = true; break;
                }
                total = total - 29 - this.GetBit(this.CalendarData[m], n);
            }
            if (isEnd) break;
        }
        cYear = 1921 + m;
        cMonth = k - n + 1;
        cDay = total;
        if (k == 12) {
            if (cMonth == Math.floor(this.CalendarData[m] / 0x10000) + 1) {
                cMonth = 1 - cMonth;
            }
            if (cMonth > Math.floor(this.CalendarData[m] / 0x10000) + 1) {
                cMonth--;
            }
        }
    };
    /**
     * @return {string}
     */
    this.GetLunadYear = function(){
        var tmp = "";
        tmp += this.lunarYear_first.charAt((cYear - 4) % 10);
        tmp += this.lunarYear_end.charAt((cYear - 4) % 12);
        tmp += this.lunarZodiac.charAt((cYear - 4) % 12);
        tmp += "年";
        return tmp;
    };
    /**
     * @return {string}
     */
    this.GetLunadMouth = function(){
        var tmp = "";
        if (cMonth < 1) {
            tmp += "(闰)";
            tmp += this.lunarDay.charAt(-cMonth - 1);
        } else {
            tmp += this.lunarDay.charAt(cMonth - 1);
        }
        tmp += "月";
        tmp += (cDay < 11) ? "初" : ((cDay < 20) ? "十" : ((cDay < 30) ? "廿" : "三十"));
        if (cDay % 10 != 0 || cDay == 10) {
            tmp += this.lunarMouth.charAt((cDay - 1) % 10);
        }
        return tmp;
    };
    /**
     * @return {string}
     */
    this.GetcDateString = function() {
        return this.GetLunadYear()+" "+this.GetLunadMouth();
    };

    /**
     * @return {string}
     */
    this.GetLunarDay = function(solarYear, solarMonth, solarDay) {
        if (solarYear < 1921 || solarYear > 2020) {/*设置年限范围*/
            return "";
        } else {
            solarMonth = (parseInt(solarMonth) > 0) ? (solarMonth - 1) : 11;
            this.e2c(solarYear, solarMonth, solarDay);
            return this.GetcDateString();
        }
    };

    this.init = function(){
        var D = new Date();
        var yy = D.getFullYear();
        var mm = D.getMonth() + 1;
        var dd = D.getDate();
        var ww = D.getDay();
        var ss = parseInt(D.getTime() / 1000);
        if (yy < 100) yy = "19" + yy;
        return this.GetLunarDay(yy, mm, dd);
    }
}