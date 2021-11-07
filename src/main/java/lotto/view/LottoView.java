package lotto.view;

import lotto.constant.LottoRank;
import lotto.domain.LottoBundle;
import lotto.domain.LottoResult;

import java.util.List;
import java.util.Map;

public class LottoView {

    public static final String FORMAT_PRINT_LOTTO_BUNDLE_STATUS = "%d개를 구매했습니다.%n";
    public static final String MESSAGE_PRINT_LOTTO_RESULT_TITLE = "\n당첨 통계\n---------\n";
    public static final String MESSAGE_EARNING_RATE_ALERT = "(기준이 1이기 때문에 결과적으로 손해라는 의미임)";
    public static final String FORMAT_LOTTO_RANK_STATUS = "%d개 일치 (%d원)- %d개%n";
    public static final String FORMAT_EARNING_RATE = "총 수익률은 %f입니다.";
    public static final String LOTTO_STATUS_START = "[";
    public static final String LOTTO_STATUS_END = "]";
    public static final String LOTTO_STATUS_DELIMITER = ", ";

    private LottoView() {
        throw new UnsupportedOperationException();
    }

    public static void printLottoBundleStatus(LottoBundle lottoBundle) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FORMAT_PRINT_LOTTO_BUNDLE_STATUS, lottoBundle.getLottoCount()));

        for (List<String> lottoStatus : lottoBundle.getStatus()) {
            sb.append(LOTTO_STATUS_START);
            sb.append(String.join(LOTTO_STATUS_DELIMITER, lottoStatus));
            sb.append(LOTTO_STATUS_END);
            sb.append("\n");
        }

        System.out.println(sb);
    }

    public static void printResult(LottoResult lottoResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_PRINT_LOTTO_RESULT_TITLE);

        sb.append(printRankStatus(lottoResult.getRankStatus()));
        sb.append(String.format(FORMAT_EARNING_RATE, lottoResult.computeEarningRate()));

        if (lottoResult.computeEarningRate() < 1) {
            sb.append(MESSAGE_EARNING_RATE_ALERT);
        }

        System.out.println(sb);
    }

    private static String printRankStatus(Map<LottoRank, Integer> rankStatus) {
        StringBuilder sb = new StringBuilder();

        rankStatus.entrySet().stream()
                .filter(rankStatusEntry -> !LottoRank.NONE.equals(rankStatusEntry.getKey()))
                .forEach(rankStatusEntry -> sb.append(String.format(FORMAT_LOTTO_RANK_STATUS
                        , rankStatusEntry.getKey().getMatchingCount()
                        , rankStatusEntry.getKey().getPrice()
                        , rankStatusEntry.getValue())));

        return sb.toString();
    }
}
