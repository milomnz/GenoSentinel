import { IsString, IsNotEmpty, MaxLength } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class UpdateTumorTypeDto{

    @ApiProperty({
        description: 'Sistema o tejido del cuerpo afectado por este tipo de tumor',
        example: 'Sistema Muscular'
    })
    
    @IsString()
    @IsNotEmpty()
    @MaxLength(70)
    systemAffected: string;
}