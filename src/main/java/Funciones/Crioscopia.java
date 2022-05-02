/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

/**
 *
 * @author diego
 */
public class Crioscopia
{
    static double agua;
    private int fpd;
    
    

    public static double obtenerAgua(int fpd) {
        //this.fpd = fpd;
        agua=0;
        if (fpd < 512 && fpd != 0) {
            switch (fpd) {
                case 511:
                    agua = 0.2;
                    break;
                case 510:
                    agua = 0.4;
                    break;
                case 509:
                    agua = 0.5;
                    break;
                case 508:
                    agua = 0.7;
                    break;
                case 507:
                    agua = 1;
                    break;
                case 506:
                    agua = 1.2;
                    break;
                case 505:
                    agua = 1.4;
                    break;
                case 504:
                    agua = 1.5;
                    break;
                case 503:
                    agua = 1.7;
                    break;
                case 502:
                    agua = 1.9;
                    break;
                case 501:
                    agua = 2;
                    break;
                case 500:
                    agua = 2.3;
                    break;
                case 499:
                    agua = 2.5;
                    break;
                case 498:
                    agua = 2.7;
                    break;
                case 497:
                    agua = 2.9;
                    break;
                case 496:
                    agua = 3.1;
                    break;
                case 495:
                    agua = 3.3;
                    break;
                case 494:
                    agua = 3.5;
                    break;
                case 493:
                    agua = 3.7;
                    break;
                case 492:
                    agua = 3.9;
                    break;
                case 491:
                    agua = 4.1;
                    break;
                case 490:
                    agua = 4.2;
                    break;
                case 489:
                    agua = 4.4;
                    break;
                case 488:
                    agua = 4.6;
                    break;
                case 487:
                    agua = 4.8;
                    break;
                case 486:
                    agua = 5.1;
                    break;
                case 485:
                    agua = 5.2;
                    break;
                case 484:
                    agua = 5.4;
                    break;
                case 483:
                    agua = 5.6;
                    break;
                case 482:
                    agua = 5.8;
                    break;
                case 481:
                    agua = 6.1;
                    break;
                case 480:
                    agua = 6.3;
                    break;
                case 479:
                    agua = 6.5;
                    break;
                case 478:
                    agua = 6.7;
                    break;
                case 477:
                    agua = 6.9;
                    break;
                case 476:
                    agua = 7.1;
                    break;
                case 475:
                    agua = 7.2;
                    break;
                case 474:
                    agua = 7.4;
                    break;
                case 473:
                    agua = 7.6;
                    break;
                case 472:
                    agua = 7.9;
                    break;
                case 471:
                    agua = 8.0;
                    break;
                case 470:
                    agua = 8.2;
                    break;
                case 469:
                    agua = 8.4;
                    break;
                case 468:
                    agua = 8.6;
                    break;
                case 467:
                    agua = 8.8;
                    break;
                case 466:
                    agua = 8.9;
                    break;
                case 465:
                    agua = 9.0;
                    break;
                case 464:
                    agua = 9.1;
                    break;
                case 463:
                    agua = 9.3;
                    break;
                case 462:
                    agua = 9.4;
                    break;
                case 461:
                    agua = 9.5;
                    break;
                case 460:
                    agua = 9.7;
                    break;
                case 459:
                    agua = 9.9;
                    break;
                case 458:
                    agua = 10.0;
                    break;
                case 457:
                    agua = 10.2;
                    break;
                case 456:
                    agua = 10.4;
                    break;
                case 455:
                    agua = 10.6;
                    break;
                case 454:
                    agua = 10.8;
                    break;
                case 453:
                    agua = 11.0;
                    break;
                case 452:
                    agua = 11.1;
                    break;
                case 451:
                    agua = 11.3;
                    break;
                case 450:
                    agua = 11.5;
                    break;
                case 449:
                    agua = 11.7;
                    break;
                case 448:
                    agua = 12.0;
                    break;
                case 447:
                    agua = 12.3;
                    break;
                case 446:
                    agua = 12.7;
                    break;
                case 445:
                    agua = 13.0;
                    break;
                case 444:
                    agua = 13.0;
                    break;
                case 443:
                    agua = 13.0;
                    break;
                case 442:
                    agua = 14.0;
                    break;
                case 441:
                    agua = 14.0;
                    break;
                case 440:
                    agua = 14.0;
                    break;
                case 439:
                    agua = 14.0;
                    break;
                case 438:
                    agua = 14.0;
                    break;
                case 437:
                    agua = 15.0;
                    break;
                case 436:
                    agua = 15.0;
                    break;
                case 435:
                    agua = 15.0;
                    break;
                case 434:
                    agua = 15.0;
                    break;
                case 433:
                    agua = 15.0;
                    break;
                case 432:
                    agua = 15.0;
                    break;
                case 431:
                    agua = 16.0;
                    break;
                case 430:
                    agua = 16.0;
                    break;
                case 429:
                    agua = 16.0;
                    break;
                case 428:
                    agua = 16.0;
                    break;
                case 427:
                    agua = 17.0;
                    break;
                case 426:
                    agua = 17.0;
                    break;
                case 425:
                    agua = 17.0;
                    break;
                case 424:
                    agua = 17.0;
                    break;
                case 423:
                    agua = 17.0;
                    break;
                case 422:
                    agua = 18.0;
                    break;
                case 421:
                    agua = 18.0;
                    break;
                case 420:
                    agua = 18.0;
                    break;
                case 419:
                    agua = 18.0;
                    break;
                case 418:
                    agua = 18.0;
                    break;
                case 417:
                    agua = 19.0;
                    break;
                case 416:
                    agua = 19.0;
                    break;
                case 415:
                    agua = 19.0;
                    break;
                case 414:
                    agua = 19.2;
                    break;
                case 413:
                    agua = 19.4;
                    break;
                case 412:
                    agua = 19.6;
                    break;
                case 411:
                    agua = 19.7;
                    break;
                case 410:
                    agua = 19.9;
                    break;
                case 409:
                    agua = 20.1;
                    break;
                case 408:
                    agua = 20.3;
                    break;
                case 407:
                    agua = 20.4;
                    break;
                case 406:
                    agua = 20.7;
                    break;
                case 405:
                    agua = 20.8;
                    break;
                case 404:
                    agua = 20.9;
                    break;
                case 403:
                    agua = 21.1;
                    break;
                case 402:
                    agua = 21.3;
                    break;
                case 401:
                    agua = 21.5;
                    break;
                case 400:
                    agua = 21.8;
                    break;
                case 399:
                    agua = 22.0;
                    break;
                case 398:
                    agua = 22.2;
                    break;
                case 397:
                    agua = 22.5;
                    break;
                default:
                    agua = 22.5;
                    break;
            }
        }

     return agua;   
    }
    
}
