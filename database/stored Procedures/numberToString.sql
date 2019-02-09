CREATE DEFINER=`web_contabilidad`@`localhost` FUNCTION `number_to_string`(n INT) RETURNS varchar(100) CHARSET latin1
BEGIN
    -- This function returns the string representation of a number.
    -- It's just an example... I'll restrict it to hundreds, but
    -- it can be extended easily.
    -- The idea is: 
    --      For each digit you need a position,
    --      For each position, you assign a string
    declare ans varchar(100);
    declare dig1, dig2, dig3, dig4, dig5, dig6, dig7 int; -- (one variable per digit)
	declare actual_value int;
    
    set ans = '';
    set actual_value = n ;	
    
    set dig7 = floor(actual_value/1000000);
    set actual_value = mod(actual_value,1000000);
    
    if dig7>0 then 
		case 
			when dig7 = 1 then set ans=concat(ans,' UN MILLON');
            when dig7 = 2 then set ans=concat(ans,' DOS MILLONES');
            when dig7 = 3 then set ans=concat(ans,' TRES MILLONES');
            when dig7 = 4 then set ans=concat(ans,' CUATRO MILLONES');
            when dig7 = 5 then set ans=concat(ans,' CINCO MILLONES');
            when dig7 = 6 then set ans=concat(ans,' SEIS MILLONES');
            when dig7 = 7 then set ans=concat(ans,' SIETE MILLONES');
            when dig7 = 8 then set ans=concat(ans,' OCHO MILLONES');
            when dig7 = 9 then set ans=concat(ans,' NUEVE MILLONES');		
        end case ;
    end if ;
    
    /*cien mil, diez mil, mil*/
    set dig6 = floor(actual_value / 100000);
    set actual_value = mod(actual_value,100000);
    
    if dig6 > 0 then
        case
            when dig6=1 then 
				if actual_value >999 then
					set ans=concat(ans, ' CIENTO');
				else 
					set ans=concat(ans, ' CIEN');
				end if;
                
            when dig6=2 then set ans=concat(ans, ' DOSCIENTOS');
            when dig6=3 then set ans=concat(ans, ' TRESCIENTOS');
            when dig6=4 then set ans=concat(ans, ' CUATROCIENTOS');
            when dig6=5 then set ans=concat(ans, ' QUINIENTOS');
            when dig6=6 then set ans=concat(ans, ' SEISCIENTOS');
            when dig6=7 then set ans=concat(ans, ' SETECIENTOS');
            when dig6=8 then set ans=concat(ans, ' OCHOCIENTOS');
            when dig6=9 then set ans=concat(ans, ' NOVECIENTOS');
            else set ans = ans;
        end case;
                
    end if;
    
    set dig5 = floor(actual_value/10000);
    
    set actual_value = mod(actual_value,10000);    
    
    set dig4 = floor(actual_value/1000);

    if dig5 = 1 then
        case
            when dig4 = 0 then set ans=concat(ans,' DIEZ');
            when dig4 = 1 then set ans=concat(ans,' ONCE');
            when dig4 = 2 then set ans=concat(ans,' DOCE');
            when dig4 = 3 then set ans=concat(ans,' TRECE');
            when dig4 = 4 then set ans=concat(ans,' CATORCE');
            when dig4 = 5 then set ans=concat(ans,' QUINCE');
            when dig4 = 6 then set ans=concat(ans,' DIECISEIS');
            when dig4 = 7 then set ans=concat(ans,' DIECISIETE');
            when dig4 = 8 then set ans=concat(ans,' DIECIOCHO');
            when dig4 = 9 then set ans=concat(ans,' DIECINUEVE');
            else set ans=ans;
        end case;
        
    else
        if dig5 > 0 then
            case
                when dig5=2 then 
					if (dig4 > 0 ) then
						set ans=concat(ans, ' VEINTI');
					else 
						set ans=concat(ans, ' VEINTE');
					end if ;
                when dig5=3 then set ans=concat(ans, ' TREINTA');
                when dig5=4 then set ans=concat(ans, ' CUARENTA');
                when dig5=5 then set ans=concat(ans, ' CINCUENTA');
                when dig5=6 then set ans=concat(ans, ' SESENTA');
                when dig5=7 then set ans=concat(ans, ' SETENTA');
                when dig5=8 then set ans=concat(ans, ' OCHENTA');
                when dig5=9 then set ans=concat(ans, ' NOVENTA');
                else set ans=ans;
            end case;
            if dig4 > 0 then
				if dig5 > 2 then
					set ans =concat(ans , ' Y');
                end if ;
			end if ;
        end if;
        if dig4 > 0 then
            case
                when dig4=1 then set ans=concat(ans, ' UN');
                when dig4=2 then set ans=concat(ans, ' DOS');
                when dig4=3 then set ans=concat(ans, ' TRES ');
                when dig4=4 then set ans=concat(ans, ' CUATRO');
                when dig4=5 then set ans=concat(ans, ' CINCO');
                when dig4=6 then set ans=concat(ans, ' SEIS');
                when dig4=7 then set ans=concat(ans, ' SIETE');
                when dig4=8 then set ans=concat(ans, ' OCHO');
                when dig4=9 then set ans=concat(ans, ' NUEVE');
                else set ans=ans;
            end case;
        end if;
    end if;

	if (actual_value > 1000) then
		set ans = concat(ans, ' MIL') ;
	end if ;
        
	set actual_value = mod(actual_value,1000);    
	/**
		centena, decena, unidad
    */
    set dig3 = floor(actual_value / 100);
    set actual_value = mod(actual_value,100);
    set dig2 = floor(actual_value/10);
    
    if dig3 > 0 then
        case
            when dig3=1 then 
				if actual_value>0  then
					set ans=concat(ans, ' CIENTO');
                else 
					set ans=concat(ans, ' CIEN');
				end if ;
            when dig3=2 then set ans=concat(ans, ' DOSCIENTOS');
            when dig3=3 then set ans=concat(ans, ' TRESCIENTOS');
            when dig3=4 then set ans=concat(ans, ' CUATROCIENTOS');
            when dig3=5 then set ans=concat(ans, ' QUINIENTOS');
            when dig3=6 then set ans=concat(ans, ' SEISCIENTOS');
            when dig3=7 then set ans=concat(ans, ' SETECIENTOS');
            when dig3=8 then set ans=concat(ans, ' OCHOCIENTOS');
            when dig3=9 then set ans=concat(ans, ' NOVECIENTOS');
            else set ans = ans;
        end case;
    end if;
    
    
    
    set actual_value = mod(actual_value,10);    
    set dig1 = actual_value;

    if dig2 = 1 then
        case
            when dig1 = 0 then set ans=concat(ans,' DIEZ');
            when dig1 = 1 then set ans=concat(ans,' ONCE');
            when dig1 = 2 then set ans=concat(ans,' DOCE');
            when dig1 = 3 then set ans=concat(ans,' TRECE');
            when dig1 = 4 then set ans=concat(ans,' CATORCE');
            when dig1 = 5 then set ans=concat(ans,' QUINCE');
            when dig1 = 6 then set ans=concat(ans,' DIECISEIS');
            when dig1 = 7 then set ans=concat(ans,' DIECISIETE');
            when dig1 = 8 then set ans=concat(ans,' DIECIOCHO');
            when dig1 = 9 then set ans=concat(ans,' DIECINUEVE');
            else set ans=ans;
        end case;
        
    else
        if dig2 > 0 then
            case
                when dig2=2 then 
					if (dig1 > 0 ) then
						set ans=concat(ans, ' VEINTI');
					else 
						set ans=concat(ans, ' VEINTE');
					end if ;
                when dig2=3 then set ans=concat(ans, ' TREINTA');
                when dig2=4 then set ans=concat(ans, ' CUARENTA');
                when dig2=5 then set ans=concat(ans, ' CINCUENTA');
                when dig2=6 then set ans=concat(ans, ' SESENTA');
                when dig2=7 then set ans=concat(ans, ' SETENTA');
                when dig2=8 then set ans=concat(ans, ' OCHENTA');
                when dig2=9 then set ans=concat(ans, ' NOVENTA');
                else set ans=ans;
            end case;
            if dig1 > 0 then
				if dig2 > 2 then
					set ans =concat(ans , ' Y');
                end if ;
			end if ;
        end if;
        if dig1 > 0 then
            case
                when dig1=1 then set ans=concat(ans, ' UNO');
                when dig1=2 then set ans=concat(ans, ' DOS');
                when dig1=3 then set ans=concat(ans, ' TRES');
                when dig1=4 then set ans=concat(ans, ' CUATRO');
                when dig1=5 then set ans=concat(ans, ' CINCO');
                when dig1=6 then set ans=concat(ans, ' SEIS');
                when dig1=7 then set ans=concat(ans, ' SIETE');
                when dig1=8 then set ans=concat(ans, ' OCHO');
                when dig1=9 then set ans=concat(ans, ' NUEVE');
                else set ans=ans;
            end case;
        end if;
    end if;

    return trim(ans);
END