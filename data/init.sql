USE marcura
CREATE TABLE public.exchange_rates
(
    ID                INT SERIAL PRIMARY KEY,
    Currency_Code     VARCHAR(3)     NOT NULL,
    Exchange_Rate     DECIMAL(15, 6) NOT NULL,
    Rate_Date         DATE           NOT NULL,
    Spread_Percentage DECIMAL(5, 2)  NOT NULL
);

CREATE TABLE public.usage_counter
(
    ID            INT SERIAL PRIMARY KEY,
    From_Currency VARCHAR(3) NOT NULL,
    To_Currency   VARCHAR(3) NOT NULL,
    Request_Date  DATE       NOT NULL,
    Counter       INT DEFAULT 0
);

UPDATE public.exchange_rates
SET Spread_Percentage = 0;
