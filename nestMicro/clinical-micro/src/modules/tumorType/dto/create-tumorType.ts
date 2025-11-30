import { IsString, IsNotEmpty, MaxLength } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class CreateTumorTypeDto{

    @ApiProperty({
        description: 'Nombre o clasificacion histologica del tumor',
        example: 'Sarcoma'
    })
    
    @IsString()
    @IsNotEmpty()
    @MaxLength(50)
    name: string;
    
    @ApiProperty({
        description: 'Sistema o tejido del cuerpo afectado por este tipo de tumor',
        example: 'Sistema Muscular'
    })
    
    @IsString()
    @IsNotEmpty()
    @MaxLength(70)
    systemAffected: string;
}