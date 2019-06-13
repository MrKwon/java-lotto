package lotto;

import java.sql.SQLException;
import java.util.List;

import lotto.domain.game.LottoResult;

public class EnrollLottoAPI {
    private static final String RATE_UNIT = "%";
    private static final String AMOUNT_UNIT = "원";

    public static String enroll(WebUILottoData webUILottoData) throws SQLException {
        accessGameData(webUILottoData, getGameDTO(webUILottoData));
        LottoResult.init();
        return TemplateEngine.render(EmptyModel.get(), "enroll.html");
    }

    private static GameDTO getGameDTO(WebUILottoData webUILottoData) {
        List<String> result = WebParser.get();
        Long rate = Math.round(LottoResult.rateOfReturn(webUILottoData.getPurchaseAmount()));
        return transferGameData(webUILottoData, result, rate);
    }

    private static void accessGameData(WebUILottoData webUILottoData, GameDTO gameDTO) throws SQLException {
        GameDAO gameDAO = new GameDAO();
        gameDAO.addGameInformation(gameDTO);
        gameDAO.addLottoNumbers(webUILottoData.getTotalLottoGames().allGames());
    }

    private static GameDTO transferGameData(WebUILottoData webUILottoData, List<String> result, Long rate) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setWinningNumbers(webUILottoData.getWinningNumbers());
        gameDTO.setBonusNumber(webUILottoData.getBonusNumber());
        gameDTO.setResult(WebParser.forSQL(result));
        gameDTO.setReturnAmount(LottoResult.resultAmount() + AMOUNT_UNIT);
        gameDTO.setReturnRate(rate + RATE_UNIT);
        return gameDTO;
    }
}
