/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.desktop.main.content.bisq_easy.trade_wizard.price;

import bisq.common.currency.Market;
import bisq.common.monetary.PriceQuote;
import bisq.desktop.common.view.Model;
import bisq.offer.Direction;
import bisq.offer.price.spec.MarketPriceSpec;
import bisq.offer.price.spec.PriceSpec;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
public class TradeWizardPriceModel implements Model {
    @Setter
    private Market market = null;
    @Setter
    private Direction direction;
    private final DoubleProperty percentage = new SimpleDoubleProperty();
    private final StringProperty percentageAsString = new SimpleStringProperty();
    private final StringProperty priceAsString = new SimpleStringProperty();
    private final BooleanProperty useFixPrice = new SimpleBooleanProperty();
    private final ObjectProperty<PriceSpec> priceSpec = new SimpleObjectProperty<>(new MarketPriceSpec());
    private final StringProperty invalidPriceErrorMessage = new SimpleStringProperty();
    @Nullable
    @Setter
    private PriceQuote lastValidPriceQuote;
    private final StringProperty feedbackSentence = new SimpleStringProperty();
    private final BooleanProperty shouldShowLearnWhyOverlay = new SimpleBooleanProperty();
    private final BooleanProperty shouldShowFeedback = new SimpleBooleanProperty();

    public void reset() {
        market = null;
        direction = null;
        percentage.set(0d);
        percentageAsString.set(null);
        priceAsString.set(null);
        useFixPrice.set(false);
        priceSpec.set(new MarketPriceSpec());
        invalidPriceErrorMessage.set(null);
        lastValidPriceQuote = null;
        feedbackSentence.set(null);
        shouldShowLearnWhyOverlay.set(false);
        shouldShowFeedback.set(false);
    }
}
